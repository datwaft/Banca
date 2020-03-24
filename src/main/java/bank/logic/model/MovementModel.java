package bank.logic.model;

import bank.data.MovementDao;
import bank.logic.Account;
import bank.logic.Movement;
import java.util.List;
import javax.persistence.EntityManager;

public class MovementModel extends MovementDao {
  private MovementModel() { }
  
  public static MovementModel getInstance() {
    return MovementModelHolder.INSTANCE;
  }
  
  public List<Movement> findByAccount(Object account) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Movement obj WHERE obj.origin.id = :id OR obj.destination.id = :id")
        .setParameter("id", (account instanceof String ? Integer.getInteger((String)account) : ((Account)account).getId()))
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting account = '" + 
        (account instanceof String ? (String)account : ((Account)account).getId()) + "' from table Movement.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  private static class MovementModelHolder {
    private static final MovementModel INSTANCE = new MovementModel();
  }
}
