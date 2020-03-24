package bank.logic.model;

import bank.data.MovementDao;
import bank.logic.Movement;
import java.util.List;
import javax.persistence.EntityManager;

public class MovementModel extends MovementDao {
  private MovementModel() { }
  
  public static MovementModel getInstance() {
    return MovementModelHolder.INSTANCE;
  }
  
  public List<Movement> findByAccount(String account) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    try {
      List<Movement> origin = em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id")
        .setParameter("id", account)
        .getResultList();
      List<Movement> destination = em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.destination.id CHAR) = :id")
        .setParameter("id", account)
        .getResultList();
      origin.addAll(destination);
      return origin;
    } catch (Exception e) {
      System.out.print("An error occurred while getting account = '" + account + "' from table Movement.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  public List<Movement> findByOrigin(String account) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id")
        .setParameter("id", account)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting origin = '" + account + "' from table Movement.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  public List<Movement> findByDestination(String account) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.destination.id CHAR) = :id")
        .setParameter("id", account)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting destination = '" + account + "' from table Movement.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  private static class MovementModelHolder {
    private static final MovementModel INSTANCE = new MovementModel();
  }
}
