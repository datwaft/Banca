package bank.logic.model;

import bank.data.UserDao;
import bank.data.PersistenceManager;

public class UserModel extends UserDao
{
  private UserModel()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
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
