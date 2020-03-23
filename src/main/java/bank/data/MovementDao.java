package bank.data;

import bank.logic.Movement;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

public class MovementDao extends AbstractFacade<Movement> implements Serializable {
  private final EntityManager em;

  public MovementDao() {
    super(Movement.class);
    em = getEntityManager();
  }

  @Override
  protected final EntityManager getEntityManager() {
    return PersistenceManager.createEntityManager();
  }

  public void create(Movement obj) {
    try {
      super.persist(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while creating the Movement.\n\n Error:" + e + "\n\n");
    }
  }

  public void edit(Movement obj) {
    try {
      super.merge(obj);
    } catch (PersistenceException e)  {
      System.out.print("An error occurred while editing the Movement.\n\n Error:" + e + "\n\n");
    }
  }

  public void delete(Movement obj) {
    try {
      super.remove(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while deleting the Movement.\n\n Error:" + e + "\n\n");
    }
  }

  public List<Movement> search(String id) {
    try {
      return em.createQuery("SELECT obj FROM Movement obj WHERE obj.id = :id")
        .setParameter("id", id)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting id = '" + id + "' from table Movement.\n\n Error:" + e + "\n\n");
    }
    return null;
  }

  public List<Movement> getAll() {
    try {
      return em.createQuery("SELECT obj FROM Movement obj")
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting all from table Movement.\n\n Error:" + e + "\n\n");
    }
    return null;
  }
}
