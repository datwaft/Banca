package bank.logic;

import bank.data.MovementDao;
import bank.data.exceptions.IllegalOrphanException;
import bank.data.exceptions.NonexistentEntityException;

public class MovementModel 
{
  MovementDao dao = MovementDao.getInstance();
  
  private MovementModel()
  {
    
  }

  public void create(Movement object) throws Exception
  {
    dao.create(object);
  }
  
  public void edit(Movement object) throws NonexistentEntityException, Exception
  {
    dao.edit(object);
  }
  
  public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
  {
    dao.destroy(id);
  }
  
  public static MovementModel getInstance() 
  {
    return MovementModelHolder.INSTANCE;
  }

  private static class MovementModelHolder 
  {
    private static final MovementModel INSTANCE = new MovementModel();
  }
}
