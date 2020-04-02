package bank.presentation.cashier.withdrawal;

import bank.logic.Account;
import java.util.ArrayList;
import java.util.List;

public class Model {
  List<Account> accountlist;
  Account account;

  public Model() {
    this.accountlist = new ArrayList<>();
    this.account = null;
  }

  public List<Account> getAccountList() {
    return accountlist;
  }

  public void setAccountList(List<Account> accountlist) {
    this.accountlist = accountlist;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
