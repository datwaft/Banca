package bank.logic.model;

import bank.data.LinkDao;
import bank.data.PersistenceManager;

public class LinkModel extends LinkDao
{
  private LinkModel()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }
  
  public static LinkModel getInstance() 
  {
    return LinkModelHolder.INSTANCE;
  }

  private static class LinkModelHolder 
  {
    private static final LinkModel INSTANCE = new LinkModel();
  }
}
