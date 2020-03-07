package bank.data;

import bank.data.exceptions.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bank.logic.Account;
import bank.logic.User;
import java.util.*;
import javax.persistence.*;

public class UserJpaController implements Serializable
{
  public UserJpaController(EntityManagerFactory emf)
  {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager()
  {
    return emf.createEntityManager();
  }

  public void create(User user) throws PreexistingEntityException, Exception
  {
    if (user.getAccountCollection() == null)
    {
      user.setAccountCollection(new ArrayList<Account>());
    }
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Collection<Account> attachedAccountCollection = new ArrayList<Account>();
      for (Account accountCollectionAccountToAttach : user.getAccountCollection())
      {
        accountCollectionAccountToAttach = em.getReference(accountCollectionAccountToAttach.getClass(), accountCollectionAccountToAttach.getId());
        attachedAccountCollection.add(accountCollectionAccountToAttach);
      }
      user.setAccountCollection(attachedAccountCollection);
      em.persist(user);
      for (Account accountCollectionAccount : user.getAccountCollection())
      {
        User oldOwnerOfAccountCollectionAccount = accountCollectionAccount.getOwner();
        accountCollectionAccount.setOwner(user);
        accountCollectionAccount = em.merge(accountCollectionAccount);
        if (oldOwnerOfAccountCollectionAccount != null)
        {
          oldOwnerOfAccountCollectionAccount.getAccountCollection().remove(accountCollectionAccount);
          oldOwnerOfAccountCollectionAccount = em.merge(oldOwnerOfAccountCollectionAccount);
        }
      }
      em.getTransaction().commit();
    }
    catch (Exception ex)
    {
      if (findUser(user.getId()) != null)
      {
        throw new PreexistingEntityException("User " + user + " already exists.", ex);
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

  public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      User persistentUser = em.find(User.class, user.getId());
      Collection<Account> accountCollectionOld = persistentUser.getAccountCollection();
      Collection<Account> accountCollectionNew = user.getAccountCollection();
      List<String> illegalOrphanMessages = null;
      for (Account accountCollectionOldAccount : accountCollectionOld)
      {
        if (!accountCollectionNew.contains(accountCollectionOldAccount))
        {
          if (illegalOrphanMessages == null)
          {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Account " + accountCollectionOldAccount + " since its owner field is not nullable.");
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
      user.setAccountCollection(accountCollectionNew);
      user = em.merge(user);
      for (Account accountCollectionNewAccount : accountCollectionNew)
      {
        if (!accountCollectionOld.contains(accountCollectionNewAccount))
        {
          User oldOwnerOfAccountCollectionNewAccount = accountCollectionNewAccount.getOwner();
          accountCollectionNewAccount.setOwner(user);
          accountCollectionNewAccount = em.merge(accountCollectionNewAccount);
          if (oldOwnerOfAccountCollectionNewAccount != null && !oldOwnerOfAccountCollectionNewAccount.equals(user))
          {
            oldOwnerOfAccountCollectionNewAccount.getAccountCollection().remove(accountCollectionNewAccount);
            oldOwnerOfAccountCollectionNewAccount = em.merge(oldOwnerOfAccountCollectionNewAccount);
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
        String id = user.getId();
        if (findUser(id) == null)
        {
          throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
      User user;
      try
      {
        user = em.getReference(User.class, id);
        user.getId();
      }
      catch (EntityNotFoundException enfe)
      {
        throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
      }
      List<String> illegalOrphanMessages = null;
      Collection<Account> accountCollectionOrphanCheck = user.getAccountCollection();
      for (Account accountCollectionOrphanCheckAccount : accountCollectionOrphanCheck)
      {
        if (illegalOrphanMessages == null)
        {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Account " + accountCollectionOrphanCheckAccount + " in its accountCollection field has a non-nullable owner field.");
      }
      if (illegalOrphanMessages != null)
      {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      em.remove(user);
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

  public List<User> findUserEntities()
  {
    return findUserEntities(true, -1, -1);
  }

  public List<User> findUserEntities(int maxResults, int firstResult)
  {
    return findUserEntities(false, maxResults, firstResult);
  }

  private List<User> findUserEntities(boolean all, int maxResults, int firstResult)
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(User.class));
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

  public User findUser(String id)
  {
    EntityManager em = getEntityManager();
    try
    {
      return em.find(User.class, id);
    }
    finally
    {
      em.close();
    }
  }

  public int getUserCount()
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<User> rt = cq.from(User.class);
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
