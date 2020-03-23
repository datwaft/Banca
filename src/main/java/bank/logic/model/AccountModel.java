package bank.logic.model;

import bank.data.AccountDao;

public class AccountModel extends AccountDao {
  private AccountModel() { }
  
  public static AccountModel getInstance() {
    return AccountModelHolder.INSTANCE;
  }

  private static class AccountModelHolder {
    private static final AccountModel INSTANCE = new AccountModel();
  }
}
