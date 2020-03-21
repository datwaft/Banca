package bank.logic.model;

import bank.data.CurrencyDao;
import bank.data.PersistenceManager;

public class CurrencyModel extends CurrencyDao
{
  private CurrencyModel()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }
  
  public static CurrencyModel getInstance() 
  {
    return CurrencyModelHolder.INSTANCE;
  }

  private static class CurrencyModelHolder 
  {
    private static final CurrencyModel INSTANCE = new CurrencyModel();
  }
}
