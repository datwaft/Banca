package bank.data;

import bank.logic.Account;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

public class AccountDao extends AbstractFacade<Account> implements Serializable
{
  private final EntityManagerFactory emf;
  private final EntityManager em;

  public AccountDao(EntityManagerFactory emf)
  {
    super(Account.class);
    this.emf = emf;
    em = getEntityManager();
  }

  @Override
  protected final EntityManager getEntityManager() 
  {
    return emf.createEntityManager();
  }

  public void create(Account obj)
  {
    try
    {
      super.persist(obj);
    } 
    catch (PersistenceException e)
    {
      System.out.print("An error occurred while creating the Account.\n\n Error:" + e + "\n\n");
    }
  }

  public void edit(Account obj)
  {
    try
    {
      super.merge(obj);
    } 
    catch (PersistenceException e) 
    {
      System.out.print("An error occurred while editing the Account.\n\n Error:" + e + "\n\n");
    }
  }

  public void delete(Account obj)
  {
    try
    {
      super.remove(obj);
    } 
    catch (PersistenceException e) 
    {
      System.out.print("An error occurred while deleting the Account.\n\n Error:" + e + "\n\n");
    }
  }

  public List<Account> search(String id)
  {
    try
    {
      return em.createQuery("SELECT obj FROM Account obj WHERE obj.id = :id")
        .setParameter("id", id)
        .getResultList();
    } 
    catch (Exception e)
    {
      System.out.print("An error occurred while getting id = '" + id + "' from table Account.\n\n Error:" + e + "\n\n");
    }
    return null;
  }

  public List<Account> getAll()
  {
    try
    {
      return em.createQuery("SELECT obj FROM Account obj")
        .getResultList();
    }
    catch (Exception e)
    {
      System.out.print("An error occurred while getting all from table Account.\n\n Error:" + e + "\n\n");
    }
    return null;
  }
}
