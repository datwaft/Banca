package bank.data;

public class CurrencyDao extends CurrencyJpaController
{
  private CurrencyDao()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }

  public static CurrencyDao getInstance() 
  {
    return AccountDaoHolder.INSTANCE;
  }

  private static class AccountDaoHolder 
  {
    private static final CurrencyDao INSTANCE = new CurrencyDao();
  }
}
