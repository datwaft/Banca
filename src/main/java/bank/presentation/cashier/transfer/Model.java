package bank.presentation.cashier.transfer;

import bank.logic.Account;
import java.util.ArrayList;
import java.util.List;

public class Model {
  List<Account> origin_id;
  List<Account> destination_id;
  Account origin;
  Account destination;

  public Model() {
    this.origin_id = new ArrayList<>();
    this.destination_id = new ArrayList<>();
    this.origin = null;
    this.destination = null;
  }

  public List<Account> getOrigin_id() {
    return origin_id;
  }

  public void setOrigin_id(List<Account> origin_id) {
    this.origin_id = origin_id;
  }

  public List<Account> getDestination_id() {
    return destination_id;
  }

  public void setDestination_id(List<Account> destination_id) {
    this.destination_id = destination_id;
  }

  public Account getOrigin() {
    return origin;
  }

  public void setOrigin(Account origin) {
    this.origin = origin;
  }

  public Account getDestination() {
    return destination;
  }

  public void setDestination(Account destination) {
    this.destination = destination;
  }
}
