/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.presentation.cashier.register;

import bank.logic.Currency;
import bank.logic.User;
import java.util.List;

/**
 *
 * @author Mario
 */
public class Model {

  User exist;
  List<Currency> currency = null;
  boolean is = false;
  String id = ""; //esto debia ser null (en caso de que algo falle) tambien se debe cambiar la condicion del campo de cedula en el view (verificar si user es nullo no)
  
  public Model() {
    this.exist = null;
  }

  public User getExist() {
    return exist;
  }

  public List<Currency> getCurrency() {
    return currency;
  }
  

  public boolean isIs() {
    return is;
  }

  public String getId() {
    return id;
  }

  public void setExist(User exist) {
    this.exist = exist;
  }

  public void setCurrency(List<Currency> currency) {
    this.currency = currency;
  }

 
  public void setIs(boolean is) {
    this.is = is;
  }

  public void setId(String id) {
    this.id = id;
  }
  
}
