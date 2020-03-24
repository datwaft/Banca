package bank.presentation.client.accounts;

import bank.logic.Account;
import bank.logic.User;
import java.util.ArrayList;
import java.util.List;

public final class Model {
  List<Account> accounts;
  User user;
  
  public Model() {
    this.setAccounts(new ArrayList<>());
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
