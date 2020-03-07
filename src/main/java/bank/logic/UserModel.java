package bank.logic;

import bank.data.UserDao;
import bank.data.exceptions.IllegalOrphanException;
import bank.data.exceptions.NonexistentEntityException;

public class UserModel 
{
  UserDao dao = UserDao.getInstance();
  
  private UserModel()
  {
    
  }

  public void create(User object) throws Exception
  {
    dao.create(object);
  }
  
  public void edit(User object) throws NonexistentEntityException, Exception
  {
    dao.edit(object);
  }
  
  public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException
  {
    dao.destroy(id);
  }
  
  public static UserModel getInstance() 
  {
    return UserModelHolder.INSTANCE;
  }

  private static class UserModelHolder 
  {
    private static final UserModel INSTANCE = new UserModel();
  }
}
