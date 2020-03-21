package bank.data;

import bank.logic.Currency;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

public class CurrencyDao extends AbstractFacade<Currency> implements Serializable
{
  private final EntityManagerFactory emf;
  private final EntityManager em;

  public CurrencyDao(EntityManagerFactory emf)
  {
    super(Currency.class);
    this.emf = emf;
    em = getEntityManager();
  }

  @Override
  protected final EntityManager getEntityManager() 
  {
    return emf.createEntityManager();
  }

  public void create(Currency obj)
  {
    try
    {
      super.persist(obj);
    } 
    catch (PersistenceException e)
    {
      System.out.print("An error occurred while creating the Currency.\n\n Error:" + e + "\n\n");
    }
  }

  public void edit(Currency obj)
  {
    try
    {
      super.merge(obj);
    } 
    catch (PersistenceException e) 
    {
      System.out.print("An error occurred while editing the Currency.\n\n Error:" + e + "\n\n");
    }
  }

  public void delete(Currency obj)
  {
    try
    {
      super.remove(obj);
    } 
    catch (PersistenceException e) 
    {
      System.out.print("An error occurred while deleting the Currency.\n\n Error:" + e + "\n\n");
    }
  }

  public List<Currency> search(String id)
  {
    try
    {
      return em.createQuery("SELECT obj FROM Currency obj WHERE obj.id = :id")
        .setParameter("id", id)
        .getResultList();
    } 
    catch (Exception e)
    {
      System.out.print("An error occurred while getting id = '" + id + "' from table Currency.\n\n Error:" + e + "\n\n");
    }
    return null;
  }

  public List<Currency> getAll()
  {
    try
    {
      return em.createQuery("SELECT obj FROM Currency obj")
        .getResultList();
    }
    catch (Exception e)
    {
      System.out.print("An error occurred while getting all from table Currency.\n\n Error:" + e + "\n\n");
    }
    return null;
  }
}
