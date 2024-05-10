package eus.ehu.concerticket.dataAccess;

import eus.ehu.concerticket.configuration.Config;
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
    this.open();
  }

  public void open() {
    open(false);
  }

  public void open(boolean initializeMode) {

    Config config = Config.getInstance();

    System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
            config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

    String fileName = config.getDatabaseName();
    if (initializeMode) {
      fileName = fileName + ";drop";
      System.out.println("Deleting the DataBase");
    }

    if (config.isDataAccessLocal()) {
      final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
              .configure() // configures settings from hibernate.cfg.xml
              .build();
      try {
        emf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
      } catch (Exception e) {
        // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
        // so destroy it manually.
        StandardServiceRegistryBuilder.destroy(registry);
      }

      db = emf.createEntityManager();
      System.out.println("DataBase opened");
    }
  }



  public void reset() {
    db.getTransaction().begin();
//    db.createQuery("DELETE FROM Table").executeUpdate();
    db.getTransaction().commit();
  }

  public void initializeDB() {

    this.reset();

    try {

      db.getTransaction().begin();

      generateTestingData();

      db.getTransaction().commit();
      System.out.println("The database has been initialized");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void generateTestingData() {
    // create domain entities and persist them
  }

  private String formatName(String name) {
    if (name == null || name.isEmpty()) {
      return name;
    }
    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public void close() {
    db.close();
    System.out.println("DataBase is closed");
  }
}