package eus.ehu.concerticket.dataAccess;

import eus.ehu.concerticket.configuration.Config;
import eus.ehu.concerticket.domain.*;
import eus.ehu.concerticket.exceptions.ConcertAlreadyExistException;
import eus.ehu.concerticket.exceptions.ConcertMustBeLaterThanTodayException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Calendar;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess {
  protected EntityManager db;
  protected EntityManagerFactory emf;

  public DataAccess() {
    this.open();
  }

  public void open() {

    Config config = Config.getInstance();

    System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
            config.isDataAccessLocal() + " getDataBaseOpenMode: " + config.getDataBaseOpenMode());

    if (config.isDataAccessLocal()) {
      final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
      try {
        emf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
      } catch (Exception e) {
        StandardServiceRegistryBuilder.destroy(registry);
        e.printStackTrace();
      }
      db = emf.createEntityManager();
      System.out.println("DataBase opened");
    }
  }

  public void reset() {
    db.getTransaction().begin();
    db.createQuery("DELETE FROM Concert").executeUpdate();
    db.createQuery("DELETE FROM Band").executeUpdate();
    db.createQuery("DELETE FROM Place").executeUpdate();
    db.createQuery("DELETE FROM User ").executeUpdate();
    db.createQuery("DELETE FROM Purchase").executeUpdate();
    System.out.println("Database reset");
    db.getTransaction().commit();
  }

  public void initializeDB() {

    this.reset();

    db.getTransaction().begin();

    try {

      generateTestingData();

      db.getTransaction().commit();
      System.out.println("The database has been initialized");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void generateTestingData() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2024, Calendar.MAY, 22, 0, 0, 0);
    Date date = calendar.getTime();

    // Create and persist sample bands
    Band band1 = new Band("B1", 2000, "A");
    db.persist(band1);

    Band band2 = new Band("B2", 2010, "J");
    db.persist(band2);

    // Create and persist sample places
    Place place1 = new Place("P1", 100, 10);
    db.persist(place1);

    Place place2 = new Place("P2", 100, 10);
    db.persist(place2);

    // Create and persist sample concerts
    Concert concert1 = new Concert(band1, place1, date, 50, 0, 100, 10);
    db.persist(concert1);

    Concert concert2 = new Concert(band2, place2, date, 60, 0, 100, 10);
    db.persist(concert2);

    // Create and persist sample clients
    User client1 = new User("b1", "b1@", "b1");
    db.persist(client1);

    User client2 = new User("b2", "b2@", "b2");
    db.persist(client2);

    // Create and persist sample staff members
    User staff1 = new User("a1", "a1@", "a1");
    db.persist(staff1);

    User staff2 = new User("a2", "a2@", "a2");
    db.persist(staff2);
  }


  public List<Purchase> getPurchases(Client client) {
      TypedQuery<Purchase> query = db.createQuery("SELECT p FROM Purchase p WHERE p.user = :client", Purchase.class);
      query.setParameter("client", client);
      return query.getResultList();
  }

  public boolean exists(String username, String email) {
    TypedQuery<Long> queryMessenger = db.createQuery("SELECT COUNT(u) FROM User u  WHERE (u.username= :username) OR (u.email = :email)", Long.class);
    queryMessenger.setParameter("email", email);
    queryMessenger.setParameter("username", username);
    Long count = queryMessenger.getSingleResult();
    return count != 0;
  }

  public boolean checkCredentials(String username, String email) {
    return !username.contains("@") && email.contains("@") && !username.isEmpty();
  }

  public boolean checkPasswords(String password, String password2) {
    return password.equals(password2) && !password.isEmpty();
  }

  public void createConcert(Band band, Place place, Date date, float price, float discount, Integer tickets, Integer maxTickets) {
    System.out.println(">> DataAccess: createConcert => band=" + band.getName() + ", place=" + place.getName() + ", date=" + date + ", price" + price + ", discount=" + discount + ", tickets=" + tickets + ", maxtickets=" + maxTickets);
    try {
      db.getTransaction().begin();

      // Check if the concert date is in the future
      if (date.compareTo(new Date()) <= 0) {
        throw new ConcertMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorConcertMustBeLaterThanToday"));
      }

      // Check if the concert already exists
      Concert concert = db.find(Concert.class, date);
      if (concert != null) {
        throw new ConcertAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
      }

      // Create and persist the concert
      concert = new Concert(band, place, date, price, discount, tickets, maxTickets);
      db.persist(concert);

      db.getTransaction().commit();
      System.out.println("Concert created: " + concert);
    } catch (Exception e) {
      // Rollback the transaction if an exception occurs
      if (db.getTransaction().isActive()) {
        db.getTransaction().rollback();
      }
      e.printStackTrace();
    }
  }


  public List<Concert> getConcerts(String band, String place, Date date, Integer tickets) {
    System.out.println(">> DataAccess: getConcerts group/place/date");

    TypedQuery<Concert> query = db.createQuery("SELECT c FROM Concert c "
            + "WHERE c.band.name=?1 and c.place.name=?2 and c.date=?3 and c.tickets=?4", Concert.class);
    query.setParameter(1, band);
    query.setParameter(2, place);
    query.setParameter(3, date);
    query.setParameter(4, tickets);

    List<Concert> concerts = query.getResultList();
    return new Vector<>(concerts);
  }

  public void setConcert(Concert concert) {
    db.getTransaction().begin();
    db.persist(concert);
    db.getTransaction().commit();
  }

  public boolean createClient(String username, String email, String password) {
    if (!exists(username, email)) {
      db.getTransaction().begin();
      Client client = new Client(username, email, password);
      db.persist(client);
      db.getTransaction().commit();
      System.out.println("Client created: " + username);
      return true;
    } else {
      System.out.println("Client already exists with username: " + username+ " or email: " + email);
      return false;
    }
  }

  public boolean isClient(String user, String password) {
    TypedQuery<Long> queryEmail = db.createQuery("SELECT COUNT(c) FROM Client c WHERE c.email = :email AND c.password = :password", Long.class);
    TypedQuery<Long> queryUsername = db.createQuery("SELECT COUNT(c) FROM Client c WHERE c.username = :username AND c.password = :password", Long.class);
    queryEmail.setParameter("email", user);
    queryEmail.setParameter("password", password);
    queryUsername.setParameter("username", user);
    queryUsername.setParameter("password", password);
    long count = queryEmail.getSingleResult() + queryUsername.getSingleResult();
    return count != 0;
  }

  public Client setCurrentClient(String user, String password) {
    TypedQuery<Client> query;
    if(user.contains("@")) {
      query = db.createQuery("SELECT c FROM Client c WHERE c.email = :user AND c.password = :password", Client.class);
    }
    else {
      query = db.createQuery("SELECT c FROM Client c WHERE c.username = :user AND c.password = :password", Client.class);
    }
    query.setParameter("user", user);
    query.setParameter("password", password);
    Client client = query.getSingleResult();
    if (client != null) {
      System.out.println("Client found: " + client.getUsername());
    } else {
      System.out.println("Client " + user + " not found");
    }
    return client;
  }

  public boolean createStaff(String username, String email, String password) {
    if (!exists(username, email)) {
      db.getTransaction().begin();
      Staff staff = new Staff(username, email, password);
      db.persist(staff);
      db.getTransaction().commit();
      System.out.println("Staff created: " + username);
      return true;
    } else {
      System.out.println("Staff already exists with username: " + username + "or email: " + email);
      return false;
    }
  }

  public boolean isStaff(String user, String password) {
    TypedQuery<Long> queryEmail = db.createQuery("SELECT COUNT(s) FROM Staff s WHERE s.email = :email AND s.password = :password", Long.class);
    TypedQuery<Long> queryUsername = db.createQuery("SELECT COUNT(s) FROM Staff s WHERE s.username = :name AND s.password = :password", Long.class);
    queryEmail.setParameter("email", user);
    queryEmail.setParameter("password", password);
    queryUsername.setParameter("name", user);
    queryUsername.setParameter("password", password);
    long count = queryEmail.getSingleResult() + queryUsername.getSingleResult();
    return count != 0;
  }

  public Staff setCurrentStaff(String user, String password) {
    TypedQuery<Staff> query;
    if(user.contains("@")) {
      query = db.createQuery("SELECT s FROM Staff s WHERE s.email = :name AND s.password = :password", Staff.class);
    }
    else {
      query = db.createQuery("SELECT s FROM Staff s WHERE s.username = :name AND s.password = :password", Staff.class);
    }
    query.setParameter("name", user);
    query.setParameter("password", password);
    Staff staff = query.getSingleResult();
    if (staff != null) {
      System.out.println("Staff found: " + staff.getUsername());
    } else {
      System.out.println("Staff " + user + " not found");
    }
    return staff;
  }

  /**
   * This method returns all the places with concerts
   *
   * @return collection of places
   */
  public List<String> getBands() {
    TypedQuery<String> query = db.createQuery("SELECT DISTINCT c.band.name FROM Concert c ORDER BY c.band.name", String.class);
    return query.getResultList();
  }

  /**
   * This method returns a band with that name
   *
   * @param name band name
   * @return Band
   */
  public Band getBand(String name) {
    TypedQuery<Band> query = db.createQuery("SELECT b FROM Band b WHERE b.name = :name", Band.class);
    query.setParameter("name", name);
    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      System.out.println("ERROR: No band with that name");
      return null;
    }
  }


  /**
   * This method returns all the places with concerts
   *
   * @return collection of places
   */
  public List<String> getPlaces() {
    TypedQuery<String> query = db.createQuery("SELECT DISTINCT c.place.name FROM Concert c ORDER BY c.place.name", String.class);
    return query.getResultList();
  }

  /**
   * This method returns a band with that name
   *
   * @param name band name
   * @return Band
   */
  public Place getPlace(String name) {
    TypedQuery<Place> query = db.createQuery("SELECT p FROM Place p WHERE p.name = :name", Place.class);
    query.setParameter("name", name);
    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      System.out.println("ERROR: No place with that name");
      return null;
    }
  }

  public List<Date> getDatesConcert(String band, String place, Integer tickets) {
    System.out.println(">> DataAccess: getDatesConcert");
    TypedQuery<Date> query = db.createQuery("SELECT DISTINCT c.date FROM Concert c WHERE c.band.name=?1 AND c.place.name=?2 AND c.maxTickets >?3 AND c.maxTickets >=?4", Date.class);
    query.setParameter(1, band);
    query.setParameter(2, place);
    query.setParameter(3, 0);
    query.setParameter(4, tickets);
    return query.getResultList();
  }
}