package bank.presentation;

import java.util.Map;
import java.util.Random;

public class Controller {
    private final String CHARACTERS;
  
    private Controller() {
      this.CHARACTERS = this.getCharacters();
    }

    public String isErroneous(String field, Map<String,String> mistakes) {
      if ((mistakes != null) && (mistakes.get(field) != null))
        return "is-invalid";
      else
        return "";
    }
    
    private String getCharacters() {
      return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    }
    
    public String generatePassword(Integer size) {
      StringBuilder salt = new StringBuilder();
      Random rnd = new Random();
      while (salt.length() < size) {
        int index = (int) (rnd.nextFloat() * CHARACTERS.length());
        salt.append(CHARACTERS.charAt(index));
      }
      return salt.toString();
    }
    
    public static Controller getInstance() {
        return ControllerHolder.INSTANCE;
    }

    private static class ControllerHolder {
        private static final Controller INSTANCE = new Controller();
    }
 }
