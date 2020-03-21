package bank.data;

import bank.logic.Link;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

public class LinkDao extends AbstractFacade<Link> implements Serializable {
  private final EntityManagerFactory emf;
  private final EntityManager em;

  public LinkDao(EntityManagerFactory emf) {
    super(Link.class);
    this.emf = emf;
    em = getEntityManager();
  }

  @Override
  protected final EntityManager getEntityManager() {
    return emf.createEntityManager();
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
    try {
      return em.createQuery("SELECT obj FROM Link obj WHERE obj.id = :id")
        .setParameter("id", id)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting id = '" + id + "' from table Link.\n\n Error:" + e + "\n\n");
    }
    return null;
  }

  public List<Link> getAll() {
    try {
      return em.createQuery("SELECT obj FROM Link obj")
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting all from table Link.\n\n Error:" + e + "\n\n");
    }
    return null;
  }
}
