package bank.logic.model;

import bank.data.CurrencyDao;

public class CurrencyModel extends CurrencyDao {
  private CurrencyModel() { }
  
  public static CurrencyModel getInstance() {
    return CurrencyModelHolder.INSTANCE;
  }

  private static class CurrencyModelHolder {
    private static final CurrencyModel INSTANCE = new CurrencyModel();
  }
}
