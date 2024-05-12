package eus.ehu.concerticket.dataAccess;

import eus.ehu.concerticket.configuration.Config;
import eus.ehu.concerticket.domain.*;
import eus.ehu.concerticket.exceptions.ConcertAlreadyExistException;
import eus.ehu.concerticket.exceptions.ConcertMustBeLaterThanTodayException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;






import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess {
  protected EntityManager db;
  protected EntityManagerFactory emf;

  public DataAccess() {
    this.open(false);
  }

  public void open(boolean initializeMode) {

    Config config = Config.getInstance();

    System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
            config.isDataAccessLocal() + " getDataBaseOpenMode: " + config.getDataBaseOpenMode());

    String fileName = config.getDatabaseName();

    if (initializeMode) {
      fileName = fileName + ";drop";
      System.out.println("Deleting the DataBase");
    }

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
    db.createQuery("DELETE FROM Group").executeUpdate();
    db.createQuery("DELETE FROM Place").executeUpdate();
    db.createQuery("DELETE FROM User").executeUpdate();
    db.createQuery("DELETE FROM Purchase").executeUpdate();
    db.getTransaction().commit();
  }

  public void initializeDB() {

    this.reset();

    try {

      db.getTransaction().begin();

      generateTestingData();

      Client client = new Client("a", "a@", "a");
      db.persist(client);

      db.getTransaction().commit();
      System.out.println("The database has been initialized");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void generateTestingData() {
    // create domain entities and persist them
  }

  public void close() {
    db.close();
    System.out.println("DataBase is closed");
  }

  public boolean exists(String username, String email) {
    TypedQuery<Long> queryMessenger = db.createQuery("SELECT COUNT(u) FROM User u  WHERE (u.username= :username) OR (u.email = :email)", Long.class);
    queryMessenger.setParameter("email", email);
    queryMessenger.setParameter("username", username);
    Long count = queryMessenger.getSingleResult();
    return count != 0;
  }

  public boolean checkCredentials(String username, String email) {
    return !username.contains("@") && email.contains("@") && !username.isEmpty() && !email.isEmpty();
  }

  public boolean checkPasswords(String password, String password2) {
    return password.equals(password2) && !password.isEmpty();
  }

  private String formatName(String name) {
    if (name == null || name.isEmpty()) {
      return name;
    }
    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public Concert createConcert(Date date, float price, Integer maxtickets, float discount, String place) throws ConcertMustBeLaterThanTodayException, ConcertAlreadyExistException {
    System.out.println(">> DataAccess: createConcert => date=" + date + ", price" + price + ", discount=" + discount + ", place=" + place.toString());
    try {
      if (new Date().compareTo(date) > 0) {
        throw new ConcertMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorConcertMustBeLaterThanToday"));
      }
      db.getTransaction().begin();

      Staff staff = new Staff();
      Concert concert = db.find(Concert.class, date);
      if (concert != null) {
        db.getTransaction().commit();
        throw new ConcertAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
      }
      //next instruction can be obviated
      db.persist(staff);
      db.getTransaction().commit();

      return concert;
    } catch (NullPointerException e) {
      db.getTransaction().commit();
      return null;
    }
  }

  public List<Concert> getConcerts(Group group, Place place, Date date) {
    System.out.println(">> DataAccess: getConcerts group/place/date");

    TypedQuery<Concert> query = db.createQuery("SELECT c FROM Concert c "
            + "WHERE c.group=?1 and c.place=?2 and c.date=?3 ", Concert.class);
    query.setParameter(1, group.getName());
    query.setParameter(2, place.getName());
    query.setParameter(3, date);

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
  public List<Place> getPlaces() {
    TypedQuery<Place> query = db.createQuery("SELECT DISTINCT c.place FROM Concert c ORDER BY c.place", Place.class);
    return query.getResultList();
  }

  /**
   * This method returns all the places with concerts, from all rides that depart from a given city
   *
   * @param group the musical group
   * @return collection of places
   */
  public List<Place> getPlaces(Group group) {
    TypedQuery<Place> query = db.createQuery("SELECT DISTINCT c.place FROM Concert c WHERE c.group=?1 ORDER BY c.place", Place.class);
    query.setParameter(1, group);
    return query.getResultList();
  }

  public List<Date> getDatesConcert(Concert concert, Place place, Integer tickets) {
    System.out.println(">> DataAccess: getDatesConcert");
    TypedQuery<Date> query = db.createQuery("SELECT DISTINCT c.date FROM Concert c WHERE c.concertID=?1 AND c.place=?2 AND c.maxTickets >?3 AND c.maxTickets >=?4", Date.class);
    query.setParameter(1, concert.getConcertID());
    query.setParameter(2, place.getName());
    query.setParameter(3, 0);
    query.setParameter(4, tickets);
    return query.getResultList();
  }
}