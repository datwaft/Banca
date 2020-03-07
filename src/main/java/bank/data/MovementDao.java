package bank.data;

public class MovementDao extends MovementJpaController
{
  private MovementDao()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }

  public static MovementDao getInstance() 
  {
    return AccountDaoHolder.INSTANCE;
  }

  private static class AccountDaoHolder 
  {
    private static final MovementDao INSTANCE = new MovementDao();
  }
}
