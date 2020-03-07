package bank.data;

public class UserDao extends UserJpaController
{
  private UserDao()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }

  public static UserDao getInstance() 
  {
    return AccountDaoHolder.INSTANCE;
  }

  private static class AccountDaoHolder 
  {
    private static final UserDao INSTANCE = new UserDao();
  }
}
