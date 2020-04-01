/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.presentation.client.transfer;

import bank.logic.Account;
import bank.logic.Link;
import java.util.List;

/**
 *
 * @author Mario
 */
public class Model {
  List<Account> origin_accounts;
  List<Link> destination_accounts; 
  Account origin;
  Account destination;
  int selected = -1;

  public Model() {
    
    this.origin_accounts = null;
    this.origin = null;
    this.destination_accounts = null;
    this.destination = null;
  }

  public void setOrigin_accounts(List<Account> origin_accounts) {
    this.origin_accounts = origin_accounts;
  }

  public void setDestination_accounts(List<Link> destination_accounts) {
    this.destination_accounts = destination_accounts;
  }

  public void setOrigin(Account origin) {
    this.origin = origin;
  }

  public void setDestination(Account destination) {
    this.destination = destination;
  }

  public void setSelected(int selected) {
    this.selected = selected;
  }

  public int getSelected() {
    return selected;
  }

  
  
  public List<Account> getOrigin_accounts() {
    return origin_accounts;
  }

  public List<Link> getDestination_accounts() {
    return destination_accounts;
  }

  public Account getOrigin() {
    return origin;
  }

  public Account getDestination() {
    return destination;
  }
  
  

  
  
  
  
  
  
}
