package bank.data;

import bank.logic.Account;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

public class AccountDao extends AbstractFacade<Account> implements Serializable {
  public AccountDao() {
    super(Account.class);
  }

  @Override
  protected final EntityManager getEntityManager() {
    return PersistenceManager.createEntityManager();
  }

  public void create(Account obj) {
    try {
      super.persist(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while creating the Account.\n\n Error:" + e + "\n\n");
    }
  }

  public void edit(Account obj) {
    try {
      super.merge(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while editing the Account.\n\n Error:" + e + "\n\n");
    }
  }

  public void delete(Account obj) {
    try {
      super.remove(obj);
    } catch (PersistenceException e) {
      System.out.print("An error occurred while deleting the Account.\n\n Error:" + e + "\n\n");
    }
  }
  
  public List<Account> search(String id) {
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Account obj WHERE obj.id = :id")
        .setParameter("id", id)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting id = '" + id + "' from table Account.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }

  public List<Account> getAll() {
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Account obj")
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting all from table Account.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
}