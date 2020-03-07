package bank.logic;

import bank.data.LinkDao;
import bank.data.exceptions.IllegalOrphanException;
import bank.data.exceptions.NonexistentEntityException;

public class LinkModel 
{
  LinkDao dao = LinkDao.getInstance();
  
  private LinkModel()
  {
    
  }

  public void create(Link object) throws Exception
  {
    dao.create(object);
  }
  
  public void edit(Link object) throws NonexistentEntityException, Exception
  {
    dao.edit(object);
  }
  
  public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
  {
    dao.destroy(id);
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
