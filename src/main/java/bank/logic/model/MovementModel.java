package bank.logic.model;

import bank.data.MovementDao;
import bank.logic.Movement;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.persistence.EntityManager;

public class MovementModel extends MovementDao {
  private MovementModel() { }
  
  public static MovementModel getInstance() {
    return MovementModelHolder.INSTANCE;
  }
  
  public List<Movement> findByAccount(String account, String from, String to) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    try {
      List<Movement> origin = this.findByOrigin(account, from, to);
      List<Movement> destination = this.findByDestination(account, from, to);
      origin.addAll(destination);
      Set<Movement> set = new LinkedHashSet<>(origin);
      origin.clear();
      origin.addAll(set);
      origin.sort((m1, m2) -> m1.getDate().compareTo(m2.getDate()));
      return origin;
    } catch (Exception e) {
      System.out.print("An error occurred while getting account = '" + account + "' from table Movement.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  public List<Movement> findByOrigin(String account, String from, String to) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      if (from.isEmpty() && to.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id")
          .setParameter("id", account)
          .getResultList();
      } else if (from.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id" 
          + " AND obj.date <= to")
          .setParameter("id", account)
          .setParameter("to", formatter.parse(to))
          .getResultList();
      } else if (to.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id" 
          + " AND :from <= obj.date")
          .setParameter("id", account)
          .setParameter("from", formatter.parse(from))
          .getResultList();
      } else {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id" 
          + " AND obj.date BETWEEN :from AND :to")
          .setParameter("id", account)
          .setParameter("to", formatter.parse(to))
          .setParameter("from", formatter.parse(from))
          .getResultList();
      }
    } catch (Exception e) {
      System.out.print("An error occurred while getting origin = '" + account + "' from table Movement.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  public List<Movement> findByOrigin(String account, String from, String to, String type) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      if (from.isEmpty() && to.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id AND type = :type")
          .setParameter("id", account)
          .setParameter("type", type)
          .getResultList();
      } else if (from.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id AND type = :type" 
          + " AND obj.date <= to")
          .setParameter("id", account)
          .setParameter("to", formatter.parse(to))
          .setParameter("type", type)
          .getResultList();
      } else if (to.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id AND type = :type" 
          + " AND :from <= obj.date")
          .setParameter("id", account)
          .setParameter("from", formatter.parse(from))
          .setParameter("type", type)
          .getResultList();
      } else {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.origin.id CHAR) = :id" 
          + " AND obj.date BETWEEN :from AND :to AND type = :type")
          .setParameter("id", account)
          .setParameter("to", formatter.parse(to))
          .setParameter("from", formatter.parse(from))
          .setParameter("type", type)
          .getResultList();
      }
    } catch (Exception e) {
      System.out.print("An error occurred while getting origin = '" + account + "' from table Movement.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  public List<Movement> findByDestination(String account, String from, String to) {
    if(account  == null)
      return null;
    EntityManager em = getEntityManager();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      if (from.isEmpty() && to.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.destination.id CHAR) = :id")
          .setParameter("id", account)
          .getResultList();
      } else if (from.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.destination.id CHAR) = :id" 
          + " AND obj.date <= to")
          .setParameter("id", account)
          .setParameter("to", formatter.parse(to))
          .getResultList();
      } else if (to.isEmpty()) {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.destination.id CHAR) = :id" 
          + " AND :from <= obj.date")
          .setParameter("id", account)
          .setParameter("from", formatter.parse(from))
          .getResultList();
      } else {
        return em.createQuery("SELECT obj FROM Movement obj WHERE CAST(obj.destination.id CHAR) = :id" 
          + " AND obj.date BETWEEN :from AND :to")
          .setParameter("id", account)
          .setParameter("to", formatter.parse(to))
          .setParameter("from", formatter.parse(from))
          .getResultList();
      }
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
