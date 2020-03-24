package bank.data;

import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PersistenceManager implements ServletContextListener {
  protected static EntityManagerFactory emf;
  final private static String DATABASE = "BANK";
  
  @Override
  public void contextInitialized(ServletContextEvent event) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    emf = Persistence.createEntityManagerFactory(DATABASE);
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent event) {
    emf.close();
  }
  
  public static EntityManager createEntityManager() {
    if (emf == null) {
      throw new IllegalStateException("Context is not initialized yet.");
    }
    return emf.createEntityManager();
  }
}