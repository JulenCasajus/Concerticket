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
import java.time.LocalDateTime;
import java.time.Month;

import java.time.ZoneId;
import java.util.*;

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

// Define dates using LocalDateTime with desired precision
    LocalDateTime dateTime1 = LocalDateTime.of(2024, Month.MAY, 21, 0, 0, 0);
    LocalDateTime dateTime2 = LocalDateTime.of(2024, Month.MAY, 22, 0, 0, 0);
    LocalDateTime dateTime3 = LocalDateTime.of(2024, Month.MAY, 23, 0, 0, 0);
    LocalDateTime dateTime4 = LocalDateTime.of(2024, Month.MAY, 24, 0, 0, 0);
    LocalDateTime dateTime5 = LocalDateTime.of(2024, Month.MAY, 25, 0, 0, 0);

// Convert LocalDateTime to Date
    Date date1 = Date.from(dateTime1.atZone(ZoneId.systemDefault()).toInstant());
    Date date2 = Date.from(dateTime2.atZone(ZoneId.systemDefault()).toInstant());
    Date date3 = Date.from(dateTime3.atZone(ZoneId.systemDefault()).toInstant());
    Date date4 = Date.from(dateTime4.atZone(ZoneId.systemDefault()).toInstant());
    Date date5 = Date.from(dateTime5.atZone(ZoneId.systemDefault()).toInstant());


    // Create and persist sample bands
    Band arcticMonkeys = new Band("Arctic Monkeys", 2002, "Alex Turner");
    db.persist(arcticMonkeys);
    Band beatles = new Band("The Beatles", 1960, "John Lennon");
    db.persist(beatles);
    Band rollingStones = new Band("The Rolling Stones", 1962, "Mick Jagger");
    db.persist(rollingStones);
    Band queen = new Band("Queen", 1970, "Freddie Mercury");
    db.persist(queen);
    Band ledZeppelin = new Band("Led Zeppelin", 1968, "Robert Plant");
    db.persist(ledZeppelin);

    // Create and persist sample places
    Place wembleyStadium = new Place("Wembley Stadium", 90000, 9);
    db.persist(wembleyStadium);
    Place madisonSquareGarden = new Place("Madison Square Garden", 20000, 9);
    db.persist(madisonSquareGarden);
    Place gelredome = new Place("Gelredome", 41000, 9);
    db.persist(gelredome);
    Place maracanaStadium = new Place("Maracanã Stadium", 78000, 9);
    db.persist(maracanaStadium);
    Place roseBowlStadium = new Place("Rose Bowl Stadium", 90000, 9);
    db.persist(roseBowlStadium);

    // Create and persist sample concerts
    Concert concert6 = new Concert(arcticMonkeys, madisonSquareGarden, date2, 6, 45, 0);
    db.persist(concert6);
    Concert concert7 = new Concert(beatles, gelredome, date3, 4, 55, 0);
    db.persist(concert7);
    Concert concert8 = new Concert(rollingStones, maracanaStadium, date4, 1, 65, 0);
    db.persist(concert8);
    Concert concert9 = new Concert(queen, roseBowlStadium, date5, 9, 70, 0);
    db.persist(concert9);
    Concert concert10 = new Concert(ledZeppelin, wembleyStadium, date1, 2, 50, 0);
    db.persist(concert10);
    Concert concert11 = new Concert(arcticMonkeys, gelredome, date3, 7, 60, 0);
    db.persist(concert11);
    Concert concert12 = new Concert(beatles, maracanaStadium, date4, 3, 70, 0);
    db.persist(concert12);
    Concert concert13 = new Concert(rollingStones, roseBowlStadium, date5, 8, 40, 0);
    db.persist(concert13);
    Concert concert14 = new Concert(queen, wembleyStadium, date1, 5, 55, 0);
    db.persist(concert14);
    Concert concert15 = new Concert(ledZeppelin, madisonSquareGarden, date2, 6, 65, 0);
    db.persist(concert15);
    Concert concert16 = new Concert(arcticMonkeys, maracanaStadium, date4, 1, 45, 0);
    db.persist(concert16);
    Concert concert17 = new Concert(beatles, roseBowlStadium, date5, 9, 55, 0);
    db.persist(concert17);
    Concert concert18 = new Concert(rollingStones, wembleyStadium, date1, 4, 70, 0);
    db.persist(concert18);
    Concert concert19 = new Concert(queen, madisonSquareGarden, date2, 2, 40, 0);
    db.persist(concert19);
    Concert concert20 = new Concert(ledZeppelin, gelredome, date3, 7, 50, 0);
    db.persist(concert20);
    Concert concert21 = new Concert(arcticMonkeys, roseBowlStadium, date5, 3, 60, 0);
    db.persist(concert21);
    Concert concert22 = new Concert(beatles, wembleyStadium, date1, 8, 65, 0);
    db.persist(concert22);
    Concert concert23 = new Concert(rollingStones, madisonSquareGarden, date2, 5, 45, 0);
    db.persist(concert23);
    Concert concert24 = new Concert(queen, gelredome, date3, 1, 70, 0);
    db.persist(concert24);
    Concert concert25 = new Concert(ledZeppelin, maracanaStadium, date4, 9, 55, 0);
    db.persist(concert25);

    // Create and persist sample clients
    User client1 = new User("a", "a@", "a");
    db.persist(client1);

    // Create and persist sample staff members
    User staff1 = new User("b", "b@", "b");
    db.persist(staff1);
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

  public void createConcert(Band band, Place place, Date date, Integer maxTickets, float price, float discount) {
    System.out.println(">> DataAccess: createConcert => band=" + band.getName() + ", place=" + place.getName() + ", date=" + date + ", maxTickets/User=" + maxTickets + ", price" + price + ", discount=" + discount);
    try {
      db.getTransaction().begin();

      // Check if the concert date is in the future
      if (date.compareTo(new Date()) <= 0) {
        throw new ConcertMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorConcertMustBeLaterThanToday"));
      }

      // Check if the concert already exists
      List<Concert> concerts = getConcerts(band.getName(), place.getName(), date, maxTickets);

      if (!concerts.isEmpty()) {
        throw new ConcertAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
      }

      // Create and persist the concert
      Concert concert = new Concert(band, place, date, maxTickets, price, discount);
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

    StringBuilder queryString = new StringBuilder("SELECT c FROM Concert c WHERE c.maxTickets > :tickets AND c.place.maxTickets > :tickets");

    if (band != null) {
      queryString.append(" AND c.band.name = :band");
    }
    if (place != null) {
      queryString.append(" AND c.place.name = :place");
    }
    if (date != null) {
      queryString.append(" AND c.date = :date");
    }

    TypedQuery<Concert> query = db.createQuery(queryString.toString(), Concert.class);

    if (band != null) {
      query.setParameter("band", band);
    }
    if (place != null) {
      query.setParameter("place", place);
    }
    if (date != null) {
      query.setParameter("date", date);
    }
    query.setParameter("tickets", tickets);

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
    StringBuilder queryString = new StringBuilder("SELECT c.date FROM Concert c WHERE c.tickets > 0 AND c.tickets >= :tickets");

    if (band != null) {
      queryString.append(" AND c.band.name = :band");
    } if (place != null) {
      queryString.append(" AND c.place.name = :place");
    }

    TypedQuery<Date> query = db.createQuery(queryString.toString(), Date.class);

    if (band != null) {
      query.setParameter("band", band);
    } if (place != null) {
      query.setParameter("place", place);
    } query.setParameter("tickets", tickets);

    return query.getResultList();
  }
}