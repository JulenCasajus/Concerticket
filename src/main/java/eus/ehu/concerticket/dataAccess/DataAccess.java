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
    LocalDateTime dateTime1 = LocalDateTime.of(2024, Month.JUNE, 1, 0, 0, 0);
    LocalDateTime dateTime2 = LocalDateTime.of(2024, Month.JUNE, 2, 0, 0, 0);
    LocalDateTime dateTime3 = LocalDateTime.of(2024, Month.JUNE, 3, 0, 0, 0);
    LocalDateTime dateTime4 = LocalDateTime.of(2024, Month.MAY, 30, 0, 0, 0);
    LocalDateTime dateTime5 = LocalDateTime.of(2024, Month.MAY, 31, 0, 0, 0);

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
    User client1 = new User("a", "a@", "a", false);
    db.persist(client1);

    // Create and persist sample staff members
    User staff1 = new User("b", "b@", "b", true);
    db.persist(staff1);
  }

  /**
   * Gets a list of purchases made by a specific user.
   *
   * @param user the user whose purchases are to be returned
   * @return a list of Purchase objects associated with the specified user
   */
  public List<Purchase> getPurchases(User user) {
    System.out.println(">> DataAccess: getPurchases user");
    TypedQuery<Purchase> query = db.createQuery("SELECT p FROM Purchase p WHERE p.user = :user", Purchase.class);
      query.setParameter("user", user);
      return query.getResultList();
  }

  /**
   * Checks if a user exists with the given username or email.
   *
   * @param username the username to check for existence
   * @param email the email to check for existence
   * @return true if a user with the given username or email exists, false otherwise
   */
  public boolean exists(String username, String email) {
    TypedQuery<Long> queryMessenger = db.createQuery("SELECT COUNT(u) FROM User u  WHERE (u.username= :username) OR (u.email = :email)", Long.class);
    queryMessenger.setParameter("email", email);
    queryMessenger.setParameter("username", username);
    Long count = queryMessenger.getSingleResult();
    return count != 0;
  }

  /**
   * Checks if a user exists with the given username or email.
   *
   * @param user the username or email to check for existence
   * @return true if a user with the given username or email exists, false otherwise
   */
  public boolean exists(String user) {
    TypedQuery<Long> queryEmail = db.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
    TypedQuery<Long> queryUsername = db.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class);
    queryEmail.setParameter("email", user);
    queryUsername.setParameter("username", user);
    long count = queryEmail.getSingleResult() + queryUsername.getSingleResult();
    return count != 0;
  }

  /**
   * Creates a new concert with the specified details.
   *
   * @param band the band performing at the concert
   * @param place the place where the concert will be held
   * @param date the date of the concert
   * @param maxTickets the maximum number of tickets available for the concert
   * @param price the price of the concert tickets
   * @param discount the discount on the concert tickets
   */
  public void createConcert(Band band, Place place, Date date, Integer maxTickets, float price, float discount) {
    System.out.println(">> DataAccess: createConcert => band=" + band.getName() + ", place=" + place.getName() + ", date=" + date + ", maxTickets/User=" + maxTickets + ", price" + price + ", discount=" + discount);
    try {
      db.getTransaction().begin();

      // Check if the concert date is in the future
      if (date.compareTo(new Date()) <= 0) throw new ConcertMustBeLaterThanTodayException();

      // Check if the concert already exists
      List<Concert> concerts = getConcerts(band.getName(), place.getName(), date, maxTickets);

      if (!concerts.isEmpty()) throw new ConcertAlreadyExistException();

      // Create and persist the concert
      Concert concert = new Concert(band, place, date, maxTickets, price, discount);
      db.persist(concert);
      db.getTransaction().commit();
      System.out.println("Concert created: " + concert);
    } catch (Exception e) {
      // Rollback the transaction if an exception occurs
      if (db.getTransaction().isActive()) db.getTransaction().rollback();
      e.printStackTrace();
    }
  }

  /**
   * Gets a list of concerts based on the specified criteria.
   *
   * @param band the name of the band performing at the concert (optional)
   * @param place the name of the place where the concert will be held (optional)
   * @param date the date of the concert (optional)
   * @param tickets the minimum number of tickets required for the concert
   * @return a list of concerts matching the specified criteria
   */
  public List<Concert> getConcerts(String band, String place, Date date, Integer tickets) {
    System.out.println(">> DataAccess: getConcerts group/place/date/tickets");
    StringBuilder queryString = new StringBuilder("SELECT c FROM Concert c WHERE c.tickets >= :tickets AND c.maxTickets >= :tickets AND c.place.maxTickets >= :tickets");

    if (band != null) queryString.append(" AND c.band.name = :band");
    if (place != null) queryString.append(" AND c.place.name = :place");
    if (date != null) queryString.append(" AND c.date = :date");

    TypedQuery<Concert> query = db.createQuery(queryString.toString(), Concert.class);

    if (band != null) query.setParameter("band", band);
    if (place != null) query.setParameter("place", place);
    if (date != null) query.setParameter("date", date);
    query.setParameter("tickets", tickets);

    List<Concert> concerts = query.getResultList();
    return new Vector<>(concerts);
  }

  /**
   * Persists the given concert to the database.
   *
   * @param concert the concert to be persisted
   */
  public void setConcert(Concert concert) {
    System.out.println(">> DataAccess: setConcerts concerts");
    db.getTransaction().begin();
    db.persist(concert);
    db.getTransaction().commit();
  }

  /**
   * Persists the purchases that current user does.
   *
   * @param user the user purchasing the tickets
   * @param concert the concert for which tickets are being purchased
   * @param tickets the number of tickets to be purchased
   * @param price the total price of the tickets
   */
  public void purchaseConcert(User user, Concert concert, int tickets, float price) {
    System.out.println(">> DataAccess: purchaseConcert => user=" + user.getUsername() + ", concert=" + concert.getBand().getName() + " at " + concert.getPlace().getName() + ", tickets=" + tickets + ", price=" + price);
    try {
      db.getTransaction().begin();

      // Deduct the tickets from the concert
      concert.setTickets(concert.getTickets() - tickets);
      db.merge(concert);

      // Create and persist the purchase
      Purchase purchase = new Purchase(user, concert, tickets, price);
      db.persist(purchase);

      db.getTransaction().commit();
    } catch (Exception e) {
      // Rollback the transaction if an exception occurs
      if (db.getTransaction().isActive()) db.getTransaction().rollback();
      e.printStackTrace();
    }
  }

  /**
   * Checks if a user exists with the given username/email and password.
   *
   * @param user the username or email of the user
   * @param password the password of the user
   * @return true if a user with the given username/email and password exists, false otherwise
   */
  public boolean isUser(String user, String password) {
    TypedQuery<Long> queryEmail = db.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.password = :password", Long.class);
    TypedQuery<Long> queryUsername = db.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :name AND u.password = :password", Long.class);
    queryEmail.setParameter("email", user);
    queryEmail.setParameter("password", password);
    queryUsername.setParameter("name", user);
    queryUsername.setParameter("password", password);
    long count = queryEmail.getSingleResult() + queryUsername.getSingleResult();
    return count != 0;
  }

  /**
   * Creates a new user with the specified details.
   *
   * @param username the username of the new user
   * @param email the email of the new user
   * @param password the password of the new user
   * @param staff a flag indicating if the user is a staff member
   * @return true if the user was successfully created, false otherwise
   */
  public boolean createUser(String username, String email, String password, boolean staff) {
    System.out.println(">> DataAccess: createUser => username=" + username + ", email=" + email + ", password=" + password + ", isStaff=" + staff);
    if (!exists(username, email)) {
      db.getTransaction().begin();
      User user = new User(username, email, password, staff);
      db.persist(user);
      db.getTransaction().commit();
      System.out.println("User created: " + username);
      return true;
    } else {
      System.out.println("User already exists with username: " + username + " or email: " + email);
      return false;
    }
  }

  /**
   * Gets the current user based on the given username/email and password.
   *
   * @param user the username or email of the user
   * @param password the password of the user
   * @return the User object corresponding to the specified username/email and password, or null if no such user exists
   */
  public User getCurrentUser(String user, String password) {
    System.out.println(">> DataAccess: getCurrentUser user/password");
    TypedQuery<User> query;
    if(user.contains("@")) {
      query = db.createQuery("SELECT u FROM User u WHERE u.email = :name AND u.password = :password", User.class);
    }
    else {
      query = db.createQuery("SELECT u FROM User u WHERE u.username = :name AND u.password = :password", User.class);
    }
    query.setParameter("name", user);
    query.setParameter("password", password);
    User currentUser = query.getSingleResult();
    if (currentUser != null) {
      System.out.println("User found: " + currentUser.getUsername());
    } else {
      System.out.println("User " + user + " not found");
    }
    return currentUser;
  }

  /**
   * Gets all the bands with concerts.
   *
   * @return list of bands names
   */
  public List<String> getBands() {
    System.out.println(">> DataAccess: getBands");
    TypedQuery<String> query = db.createQuery("SELECT DISTINCT c.band.name FROM Concert c ORDER BY c.band.name", String.class);
    return query.getResultList();
  }

  /**
   * Gets a band with the name given.
   *
   * @param name band name
   * @return the Band object corresponding to the specified name
   */
  public Band getBand(String name) {
    System.out.println(">> DataAccess: getBand name");
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
   * Gets all the places with concerts.
   *
   * @return list of places names
   */
  public List<String> getPlaces() {
    System.out.println(">> DataAccess: getPlaces");
    TypedQuery<String> query = db.createQuery("SELECT DISTINCT c.place.name FROM Concert c ORDER BY c.place.name", String.class);

    return query.getResultList();
  }

  /**
   * Gets a place with the name given.
   *
   * @param name place name
   * @return the Place object corresponding to the specified name
   */
  public Place getPlace(String name) {
    System.out.println(">> DataAccess: getPlace name");
    TypedQuery<Place> query = db.createQuery("SELECT p FROM Place p WHERE p.name = :name", Place.class);
    query.setParameter("name", name);
    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      System.out.println("ERROR: No place with that name");
      return null;
    }
  }

  /**
   * Gets the dates for the concerts with the parameters given.
   *
   * @param band band name
   * @param place place name
   * @param tickets tickets
   * @return list of dates
   */
  public List<Date> getDatesConcert(String band, String place, Integer tickets) {
    System.out.println(">> DataAccess: getDatesConcert band/place/tickets");
    StringBuilder queryString = new StringBuilder("SELECT c.date FROM Concert c WHERE c.tickets >= :tickets AND c.maxTickets >= :tickets AND c.place.maxTickets >= :tickets");

    if (band != null) queryString.append(" AND c.band.name = :band");
    if (place != null) queryString.append(" AND c.place.name = :place");

    TypedQuery<Date> query = db.createQuery(queryString.toString(), Date.class);

    if (band != null) query.setParameter("band", band);
    if (place != null) query.setParameter("place", place);
    query.setParameter("tickets", tickets);
    return query.getResultList();
  }
}