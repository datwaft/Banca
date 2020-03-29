package bank.logic.model;

import bank.data.AccountDao;
import bank.logic.Account;
import bank.logic.User;
import java.util.List;
import javax.persistence.EntityManager;

public class AccountModel extends AccountDao {
  private AccountModel() { }
  
  public static AccountModel getInstance() {
    return AccountModelHolder.INSTANCE;
  }

  public List<Account> findByOwner(String owner) {
    if(owner  == null)
      return null;
    EntityManager em = getEntityManager();
    try {
      return em.createQuery("SELECT obj FROM Account obj WHERE obj.owner.id = :id")
        .setParameter("id", owner)
        .getResultList();
    } catch (Exception e) {
      System.out.print("An error occurred while getting owner = '" + owner + "' from table Account.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  public Account findById(Integer id) {
    if(id  == null)
      return null;
    EntityManager em = getEntityManager();
    try {
      return (Account)em.createQuery("SELECT obj FROM Account obj WHERE obj.id = :id")
        .setParameter("id", id).getSingleResult();
        
    } catch (Exception e) {
      System.out.print("An error occurred while getting id = '" + id + "' from table Account.\n\n Error:" + e + "\n\n");
      return null;
    } finally {
      em.close();
    }
  }
  
  private static class AccountModelHolder {
    private static final AccountModel INSTANCE = new AccountModel();
  }
}
