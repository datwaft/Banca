package bank.logic.model;

import bank.data.UserDao;

public class UserModel extends UserDao {
  private UserModel() { }
  
  public static UserModel getInstance() {
    return UserModelHolder.INSTANCE;
  }

  private static class UserModelHolder {
    private static final UserModel INSTANCE = new UserModel();
  }
}
