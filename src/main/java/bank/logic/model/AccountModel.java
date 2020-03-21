package bank.logic.model;

import bank.data.AccountDao;
import bank.data.PersistenceManager;

public class AccountModel extends AccountDao
{
  private AccountModel()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }
  
  public static AccountModel getInstance() 
  {
    return AccountModelHolder.INSTANCE;
  }

  private static class AccountModelHolder 
  {
    private static final AccountModel INSTANCE = new AccountModel();
  }
}
