package bank.data;

import bank.logic.Link;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

public class LinkDao extends AbstractFacade<Link> implements Serializable {
  public LinkDao() {
    super(Link.class);
  }

  @Override
  protected final EntityManager getEntityManager() {
    return PersistenceManager.createEntityManager();
  }

  public void create(Link obj) {
    try {
      super.persist(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while creating the Link.\n\n Error:" + e + "\n\n");
    }
  }

  public void edit(Link obj) {
    try {
      super.merge(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while editing the Link.\n\n Error:" + e + "\n\n");
    }
  }

  public void delete(Link obj) {
    try {
      super.remove(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while deleting the Link.\n\n Error:" + e + "\n\n");
    }
  }

  public List<Link> search(String id) {
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Link obj WHERE obj.id = :id")
        .setParameter("id", id)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting id = '" + id + "' from table Link.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  public List<Link> searchByLinked(int linked) {
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Link obj WHERE obj.owner.id like :linked")
        .setParameter("linked", linked)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting linked = '" + linked + "' from table Link.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }

  public List<Link> getAll() {
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Link obj")
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting all from table Link.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
}