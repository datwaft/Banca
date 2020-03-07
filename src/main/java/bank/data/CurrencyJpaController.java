package bank.data;

import bank.data.exceptions.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bank.logic.Account;
import bank.logic.Currency;
import java.util.*;
import javax.persistence.*;

public class CurrencyJpaController implements Serializable
{
  public CurrencyJpaController(EntityManagerFactory emf)
  {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager()
  {
    return emf.createEntityManager();
  }

  public void create(Currency currency) throws PreexistingEntityException, Exception
  {
    if (currency.getAccountCollection() == null)
    {
      currency.setAccountCollection(new ArrayList<Account>());
    }
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Collection<Account> attachedAccountCollection = new ArrayList<Account>();
      for (Account accountCollectionAccountToAttach : currency.getAccountCollection())
      {
        accountCollectionAccountToAttach = em.getReference(accountCollectionAccountToAttach.getClass(), accountCollectionAccountToAttach.getId());
        attachedAccountCollection.add(accountCollectionAccountToAttach);
      }
      currency.setAccountCollection(attachedAccountCollection);
      em.persist(currency);
      for (Account accountCollectionAccount : currency.getAccountCollection())
      {
        Currency oldCurrencyOfAccountCollectionAccount = accountCollectionAccount.getCurrency();
        accountCollectionAccount.setCurrency(currency);
        accountCollectionAccount = em.merge(accountCollectionAccount);
        if (oldCurrencyOfAccountCollectionAccount != null)
        {
          oldCurrencyOfAccountCollectionAccount.getAccountCollection().remove(accountCollectionAccount);
          oldCurrencyOfAccountCollectionAccount = em.merge(oldCurrencyOfAccountCollectionAccount);
        }
      }
      em.getTransaction().commit();
    }
    catch (Exception ex)
    {
      if (findCurrency(currency.getCode()) != null)
      {
        throw new PreexistingEntityException("Currency " + currency + " already exists.", ex);
      }
      throw ex;
    }
    finally
    {
      if (em != null)
      {
        em.close();
      }
    }
  }

  public void edit(Currency currency) throws IllegalOrphanException, NonexistentEntityException, Exception
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Currency persistentCurrency = em.find(Currency.class, currency.getCode());
      Collection<Account> accountCollectionOld = persistentCurrency.getAccountCollection();
      Collection<Account> accountCollectionNew = currency.getAccountCollection();
      List<String> illegalOrphanMessages = null;
      for (Account accountCollectionOldAccount : accountCollectionOld)
      {
        if (!accountCollectionNew.contains(accountCollectionOldAccount))
        {
          if (illegalOrphanMessages == null)
          {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Account " + accountCollectionOldAccount + " since its currency field is not nullable.");
        }
      }
      if (illegalOrphanMessages != null)
      {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      Collection<Account> attachedAccountCollectionNew = new ArrayList<Account>();
      for (Account accountCollectionNewAccountToAttach : accountCollectionNew)
      {
        accountCollectionNewAccountToAttach = em.getReference(accountCollectionNewAccountToAttach.getClass(), accountCollectionNewAccountToAttach.getId());
        attachedAccountCollectionNew.add(accountCollectionNewAccountToAttach);
      }
      accountCollectionNew = attachedAccountCollectionNew;
      currency.setAccountCollection(accountCollectionNew);
      currency = em.merge(currency);
      for (Account accountCollectionNewAccount : accountCollectionNew)
      {
        if (!accountCollectionOld.contains(accountCollectionNewAccount))
        {
          Currency oldCurrencyOfAccountCollectionNewAccount = accountCollectionNewAccount.getCurrency();
          accountCollectionNewAccount.setCurrency(currency);
          accountCollectionNewAccount = em.merge(accountCollectionNewAccount);
          if (oldCurrencyOfAccountCollectionNewAccount != null && !oldCurrencyOfAccountCollectionNewAccount.equals(currency))
          {
            oldCurrencyOfAccountCollectionNewAccount.getAccountCollection().remove(accountCollectionNewAccount);
            oldCurrencyOfAccountCollectionNewAccount = em.merge(oldCurrencyOfAccountCollectionNewAccount);
          }
        }
      }
      em.getTransaction().commit();
    }
    catch (Exception ex)
    {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0)
      {
        String id = currency.getCode();
        if (findCurrency(id) == null)
        {
          throw new NonexistentEntityException("The currency with id " + id + " no longer exists.");
        }
      }
      throw ex;
    }
    finally
    {
      if (em != null)
      {
        em.close();
      }
    }
  }

  public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Currency currency;
      try
      {
        currency = em.getReference(Currency.class, id);
        currency.getCode();
      }
      catch (EntityNotFoundException enfe)
      {
        throw new NonexistentEntityException("The currency with id " + id + " no longer exists.", enfe);
      }
      List<String> illegalOrphanMessages = null;
      Collection<Account> accountCollectionOrphanCheck = currency.getAccountCollection();
      for (Account accountCollectionOrphanCheckAccount : accountCollectionOrphanCheck)
      {
        if (illegalOrphanMessages == null)
        {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This Currency (" + currency + ") cannot be destroyed since the Account " + accountCollectionOrphanCheckAccount + " in its accountCollection field has a non-nullable currency field.");
      }
      if (illegalOrphanMessages != null)
      {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      em.remove(currency);
      em.getTransaction().commit();
    }
    finally
    {
      if (em != null)
      {
        em.close();
      }
    }
  }

  public List<Currency> findCurrencyEntities()
  {
    return findCurrencyEntities(true, -1, -1);
  }

  public List<Currency> findCurrencyEntities(int maxResults, int firstResult)
  {
    return findCurrencyEntities(false, maxResults, firstResult);
  }

  private List<Currency> findCurrencyEntities(boolean all, int maxResults, int firstResult)
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Currency.class));
      Query q = em.createQuery(cq);
      if (!all)
      {
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
      }
      return q.getResultList();
    }
    finally
    {
      em.close();
    }
  }

  public Currency findCurrency(String id)
  {
    EntityManager em = getEntityManager();
    try
    {
      return em.find(Currency.class, id);
    }
    finally
    {
      em.close();
    }
  }

  public int getCurrencyCount()
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Currency> rt = cq.from(Currency.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    }
    finally
    {
      em.close();
    }
  }

}
