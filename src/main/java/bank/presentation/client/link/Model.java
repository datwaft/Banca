package bank.presentation.client.link;

import bank.logic.Account;
import java.util.ArrayList;
import java.util.List;

public class Model {
  List<Account> accounts;
  Account to_link;
  Account origin;
  int selected;

  public Model() {
    this.setAccounts(new ArrayList<Account>());
    this.setTo_link(null);
    this.setOrigin(null);
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }

  public void setTo_link(Account to_link) {
    this.to_link = to_link;
  }

  public void setOrigin(Account origin) {
    this.origin = origin;
  }

  public void setSelected(int selected) {
    this.selected = selected;
  }
  

  public List<Account> getAccounts() {
    return accounts;
  }

  public Account getTo_link() {
    return to_link;
  }

  public Account getOrigin() {
    return origin;
  }

  public int getSelected() {
    return selected;
  }

  

}
