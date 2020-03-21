package bank.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager
{
  protected EntityManagerFactory emf;
  
  private PersistenceManager()
  {
    emf = null;
  }
  
  public EntityManagerFactory getEntityManagerFactory()
  {
    if (emf == null)
      createEntityManagerFactory();
    return emf;
  }
  
  public void closeEntityManagerFactory() 
  {
    if (emf != null)
    {
      emf.close();
      emf = null;
    }
  }
  
  protected void createEntityManagerFactory() 
  {
    this.emf = Persistence.createEntityManagerFactory("BANK");
  }
  
  public static PersistenceManager getInstance()
  {
    return PersistenceManagerHolder.INSTANCE;
  }
  
  private static class PersistenceManagerHolder
  {
    private static final PersistenceManager INSTANCE = new PersistenceManager();
  }
}
