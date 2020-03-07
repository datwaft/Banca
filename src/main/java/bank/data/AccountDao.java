package bank.data;

public class AccountDao extends AccountJpaController
{
  private AccountDao()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }

  public static AccountDao getInstance() 
  {
    return AccountDaoHolder.INSTANCE;
  }

  private static class AccountDaoHolder 
  {
    private static final AccountDao INSTANCE = new AccountDao();
  }
}
