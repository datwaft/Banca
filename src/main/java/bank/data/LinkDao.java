package bank.data;

public class LinkDao extends LinkJpaController
{
  private LinkDao()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }

  public static LinkDao getInstance() 
  {
    return AccountDaoHolder.INSTANCE;
  }

  private static class AccountDaoHolder 
  {
    private static final LinkDao INSTANCE = new LinkDao();
  }
}
