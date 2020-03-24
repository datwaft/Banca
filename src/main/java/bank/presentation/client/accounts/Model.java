package bank.presentation.client.accounts;

import bank.logic.Account;
import java.util.ArrayList;
import java.util.List;

public final class Model {
  List<Account> accounts;
  
  public Model() {
    this.setAccounts(new ArrayList<>());
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }
}
