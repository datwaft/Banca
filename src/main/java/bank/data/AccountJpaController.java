package bank.data;

import bank.data.exceptions.IllegalOrphanException;
import bank.data.exceptions.NonexistentEntityException;
import bank.logic.*;
import bank.logic.Currency;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import javax.persistence.*;

public class AccountJpaController implements Serializable
{
  public AccountJpaController(EntityManagerFactory emf)
  {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager()
  {
    return emf.createEntityManager();
  }

  public void create(Account account)
  {
    if (account.getLinksCollection() == null)
    {
      account.setLinksCollection(new ArrayList<Links>());
    }
    if (account.getLinksCollection1() == null)
    {
      account.setLinksCollection1(new ArrayList<Links>());
    }
    if (account.getMovementCollection() == null)
    {
      account.setMovementCollection(new ArrayList<Movement>());
    }
    if (account.getMovementCollection1() == null)
    {
      account.setMovementCollection1(new ArrayList<Movement>());
    }
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Currency currency = account.getCurrency();
      if (currency != null)
      {
        currency = em.getReference(currency.getClass(), currency.getCode());
        account.setCurrency(currency);
      }
      User owner = account.getOwner();
      if (owner != null)
      {
        owner = em.getReference(owner.getClass(), owner.getId());
        account.setOwner(owner);
      }
      Collection<Links> attachedLinksCollection = new ArrayList<Links>();
      for (Links linksCollectionLinksToAttach : account.getLinksCollection())
      {
        linksCollectionLinksToAttach = em.getReference(linksCollectionLinksToAttach.getClass(), linksCollectionLinksToAttach.getId());
        attachedLinksCollection.add(linksCollectionLinksToAttach);
      }
      account.setLinksCollection(attachedLinksCollection);
      Collection<Links> attachedLinksCollection1 = new ArrayList<Links>();
      for (Links linksCollection1LinksToAttach : account.getLinksCollection1())
      {
        linksCollection1LinksToAttach = em.getReference(linksCollection1LinksToAttach.getClass(), linksCollection1LinksToAttach.getId());
        attachedLinksCollection1.add(linksCollection1LinksToAttach);
      }
      account.setLinksCollection1(attachedLinksCollection1);
      Collection<Movement> attachedMovementCollection = new ArrayList<Movement>();
      for (Movement movementCollectionMovementToAttach : account.getMovementCollection())
      {
        movementCollectionMovementToAttach = em.getReference(movementCollectionMovementToAttach.getClass(), movementCollectionMovementToAttach.getId());
        attachedMovementCollection.add(movementCollectionMovementToAttach);
      }
      account.setMovementCollection(attachedMovementCollection);
      Collection<Movement> attachedMovementCollection1 = new ArrayList<Movement>();
      for (Movement movementCollection1MovementToAttach : account.getMovementCollection1())
      {
        movementCollection1MovementToAttach = em.getReference(movementCollection1MovementToAttach.getClass(), movementCollection1MovementToAttach.getId());
        attachedMovementCollection1.add(movementCollection1MovementToAttach);
      }
      account.setMovementCollection1(attachedMovementCollection1);
      em.persist(account);
      if (currency != null)
      {
        currency.getAccountCollection().add(account);
        currency = em.merge(currency);
      }
      if (owner != null)
      {
        owner.getAccountCollection().add(account);
        owner = em.merge(owner);
      }
      for (Links linksCollectionLinks : account.getLinksCollection())
      {
        Account oldOwnerOfLinksCollectionLinks = linksCollectionLinks.getOwner();
        linksCollectionLinks.setOwner(account);
        linksCollectionLinks = em.merge(linksCollectionLinks);
        if (oldOwnerOfLinksCollectionLinks != null)
        {
          oldOwnerOfLinksCollectionLinks.getLinksCollection().remove(linksCollectionLinks);
          oldOwnerOfLinksCollectionLinks = em.merge(oldOwnerOfLinksCollectionLinks);
        }
      }
      for (Links linksCollection1Links : account.getLinksCollection1())
      {
        Account oldLinkedAccountOfLinksCollection1Links = linksCollection1Links.getLinkedAccount();
        linksCollection1Links.setLinkedAccount(account);
        linksCollection1Links = em.merge(linksCollection1Links);
        if (oldLinkedAccountOfLinksCollection1Links != null)
        {
          oldLinkedAccountOfLinksCollection1Links.getLinksCollection1().remove(linksCollection1Links);
          oldLinkedAccountOfLinksCollection1Links = em.merge(oldLinkedAccountOfLinksCollection1Links);
        }
      }
      for (Movement movementCollectionMovement : account.getMovementCollection())
      {
        Account oldOriginOfMovementCollectionMovement = movementCollectionMovement.getOrigin();
        movementCollectionMovement.setOrigin(account);
        movementCollectionMovement = em.merge(movementCollectionMovement);
        if (oldOriginOfMovementCollectionMovement != null)
        {
          oldOriginOfMovementCollectionMovement.getMovementCollection().remove(movementCollectionMovement);
          oldOriginOfMovementCollectionMovement = em.merge(oldOriginOfMovementCollectionMovement);
        }
      }
      for (Movement movementCollection1Movement : account.getMovementCollection1())
      {
        Account oldDestinationOfMovementCollection1Movement = movementCollection1Movement.getDestination();
        movementCollection1Movement.setDestination(account);
        movementCollection1Movement = em.merge(movementCollection1Movement);
        if (oldDestinationOfMovementCollection1Movement != null)
        {
          oldDestinationOfMovementCollection1Movement.getMovementCollection1().remove(movementCollection1Movement);
          oldDestinationOfMovementCollection1Movement = em.merge(oldDestinationOfMovementCollection1Movement);
        }
      }
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

  public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, Exception
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Account persistentAccount = em.find(Account.class, account.getId());
      Currency currencyOld = persistentAccount.getCurrency();
      Currency currencyNew = account.getCurrency();
      User ownerOld = persistentAccount.getOwner();
      User ownerNew = account.getOwner();
      Collection<Links> linksCollectionOld = persistentAccount.getLinksCollection();
      Collection<Links> linksCollectionNew = account.getLinksCollection();
      Collection<Links> linksCollection1Old = persistentAccount.getLinksCollection1();
      Collection<Links> linksCollection1New = account.getLinksCollection1();
      Collection<Movement> movementCollectionOld = persistentAccount.getMovementCollection();
      Collection<Movement> movementCollectionNew = account.getMovementCollection();
      Collection<Movement> movementCollection1Old = persistentAccount.getMovementCollection1();
      Collection<Movement> movementCollection1New = account.getMovementCollection1();
      List<String> illegalOrphanMessages = null;
      for (Links linksCollectionOldLinks : linksCollectionOld)
      {
        if (!linksCollectionNew.contains(linksCollectionOldLinks))
        {
          if (illegalOrphanMessages == null)
          {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Links " + linksCollectionOldLinks + " since its owner field is not nullable.");
        }
      }
      for (Links linksCollection1OldLinks : linksCollection1Old)
      {
        if (!linksCollection1New.contains(linksCollection1OldLinks))
        {
          if (illegalOrphanMessages == null)
          {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Links " + linksCollection1OldLinks + " since its linkedAccount field is not nullable.");
        }
      }
      if (illegalOrphanMessages != null)
      {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      if (currencyNew != null)
      {
        currencyNew = em.getReference(currencyNew.getClass(), currencyNew.getCode());
        account.setCurrency(currencyNew);
      }
      if (ownerNew != null)
      {
        ownerNew = em.getReference(ownerNew.getClass(), ownerNew.getId());
        account.setOwner(ownerNew);
      }
      Collection<Links> attachedLinksCollectionNew = new ArrayList<Links>();
      for (Links linksCollectionNewLinksToAttach : linksCollectionNew)
      {
        linksCollectionNewLinksToAttach = em.getReference(linksCollectionNewLinksToAttach.getClass(), linksCollectionNewLinksToAttach.getId());
        attachedLinksCollectionNew.add(linksCollectionNewLinksToAttach);
      }
      linksCollectionNew = attachedLinksCollectionNew;
      account.setLinksCollection(linksCollectionNew);
      Collection<Links> attachedLinksCollection1New = new ArrayList<Links>();
      for (Links linksCollection1NewLinksToAttach : linksCollection1New)
      {
        linksCollection1NewLinksToAttach = em.getReference(linksCollection1NewLinksToAttach.getClass(), linksCollection1NewLinksToAttach.getId());
        attachedLinksCollection1New.add(linksCollection1NewLinksToAttach);
      }
      linksCollection1New = attachedLinksCollection1New;
      account.setLinksCollection1(linksCollection1New);
      Collection<Movement> attachedMovementCollectionNew = new ArrayList<Movement>();
      for (Movement movementCollectionNewMovementToAttach : movementCollectionNew)
      {
        movementCollectionNewMovementToAttach = em.getReference(movementCollectionNewMovementToAttach.getClass(), movementCollectionNewMovementToAttach.getId());
        attachedMovementCollectionNew.add(movementCollectionNewMovementToAttach);
      }
      movementCollectionNew = attachedMovementCollectionNew;
      account.setMovementCollection(movementCollectionNew);
      Collection<Movement> attachedMovementCollection1New = new ArrayList<Movement>();
      for (Movement movementCollection1NewMovementToAttach : movementCollection1New)
      {
        movementCollection1NewMovementToAttach = em.getReference(movementCollection1NewMovementToAttach.getClass(), movementCollection1NewMovementToAttach.getId());
        attachedMovementCollection1New.add(movementCollection1NewMovementToAttach);
      }
      movementCollection1New = attachedMovementCollection1New;
      account.setMovementCollection1(movementCollection1New);
      account = em.merge(account);
      if (currencyOld != null && !currencyOld.equals(currencyNew))
      {
        currencyOld.getAccountCollection().remove(account);
        currencyOld = em.merge(currencyOld);
      }
      if (currencyNew != null && !currencyNew.equals(currencyOld))
      {
        currencyNew.getAccountCollection().add(account);
        currencyNew = em.merge(currencyNew);
      }
      if (ownerOld != null && !ownerOld.equals(ownerNew))
      {
        ownerOld.getAccountCollection().remove(account);
        ownerOld = em.merge(ownerOld);
      }
      if (ownerNew != null && !ownerNew.equals(ownerOld))
      {
        ownerNew.getAccountCollection().add(account);
        ownerNew = em.merge(ownerNew);
      }
      for (Links linksCollectionNewLinks : linksCollectionNew)
      {
        if (!linksCollectionOld.contains(linksCollectionNewLinks))
        {
          Account oldOwnerOfLinksCollectionNewLinks = linksCollectionNewLinks.getOwner();
          linksCollectionNewLinks.setOwner(account);
          linksCollectionNewLinks = em.merge(linksCollectionNewLinks);
          if (oldOwnerOfLinksCollectionNewLinks != null && !oldOwnerOfLinksCollectionNewLinks.equals(account))
          {
            oldOwnerOfLinksCollectionNewLinks.getLinksCollection().remove(linksCollectionNewLinks);
            oldOwnerOfLinksCollectionNewLinks = em.merge(oldOwnerOfLinksCollectionNewLinks);
          }
        }
      }
      for (Links linksCollection1NewLinks : linksCollection1New)
      {
        if (!linksCollection1Old.contains(linksCollection1NewLinks))
        {
          Account oldLinkedAccountOfLinksCollection1NewLinks = linksCollection1NewLinks.getLinkedAccount();
          linksCollection1NewLinks.setLinkedAccount(account);
          linksCollection1NewLinks = em.merge(linksCollection1NewLinks);
          if (oldLinkedAccountOfLinksCollection1NewLinks != null && !oldLinkedAccountOfLinksCollection1NewLinks.equals(account))
          {
            oldLinkedAccountOfLinksCollection1NewLinks.getLinksCollection1().remove(linksCollection1NewLinks);
            oldLinkedAccountOfLinksCollection1NewLinks = em.merge(oldLinkedAccountOfLinksCollection1NewLinks);
          }
        }
      }
      for (Movement movementCollectionOldMovement : movementCollectionOld)
      {
        if (!movementCollectionNew.contains(movementCollectionOldMovement))
        {
          movementCollectionOldMovement.setOrigin(null);
          movementCollectionOldMovement = em.merge(movementCollectionOldMovement);
        }
      }
      for (Movement movementCollectionNewMovement : movementCollectionNew)
      {
        if (!movementCollectionOld.contains(movementCollectionNewMovement))
        {
          Account oldOriginOfMovementCollectionNewMovement = movementCollectionNewMovement.getOrigin();
          movementCollectionNewMovement.setOrigin(account);
          movementCollectionNewMovement = em.merge(movementCollectionNewMovement);
          if (oldOriginOfMovementCollectionNewMovement != null && !oldOriginOfMovementCollectionNewMovement.equals(account))
          {
            oldOriginOfMovementCollectionNewMovement.getMovementCollection().remove(movementCollectionNewMovement);
            oldOriginOfMovementCollectionNewMovement = em.merge(oldOriginOfMovementCollectionNewMovement);
          }
        }
      }
      for (Movement movementCollection1OldMovement : movementCollection1Old)
      {
        if (!movementCollection1New.contains(movementCollection1OldMovement))
        {
          movementCollection1OldMovement.setDestination(null);
          movementCollection1OldMovement = em.merge(movementCollection1OldMovement);
        }
      }
      for (Movement movementCollection1NewMovement : movementCollection1New)
      {
        if (!movementCollection1Old.contains(movementCollection1NewMovement))
        {
          Account oldDestinationOfMovementCollection1NewMovement = movementCollection1NewMovement.getDestination();
          movementCollection1NewMovement.setDestination(account);
          movementCollection1NewMovement = em.merge(movementCollection1NewMovement);
          if (oldDestinationOfMovementCollection1NewMovement != null && !oldDestinationOfMovementCollection1NewMovement.equals(account))
          {
            oldDestinationOfMovementCollection1NewMovement.getMovementCollection1().remove(movementCollection1NewMovement);
            oldDestinationOfMovementCollection1NewMovement = em.merge(oldDestinationOfMovementCollection1NewMovement);
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
        Integer id = account.getId();
        if (findAccount(id) == null)
        {
          throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
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

  public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Account account;
      try
      {
        account = em.getReference(Account.class, id);
        account.getId();
      }
      catch (EntityNotFoundException enfe)
      {
        throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
      }
      List<String> illegalOrphanMessages = null;
      Collection<Links> linksCollectionOrphanCheck = account.getLinksCollection();
      for (Links linksCollectionOrphanCheckLinks : linksCollectionOrphanCheck)
      {
        if (illegalOrphanMessages == null)
        {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Links " + linksCollectionOrphanCheckLinks + " in its linksCollection field has a non-nullable owner field.");
      }
      Collection<Links> linksCollection1OrphanCheck = account.getLinksCollection1();
      for (Links linksCollection1OrphanCheckLinks : linksCollection1OrphanCheck)
      {
        if (illegalOrphanMessages == null)
        {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Links " + linksCollection1OrphanCheckLinks + " in its linksCollection1 field has a non-nullable linkedAccount field.");
      }
      if (illegalOrphanMessages != null)
      {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      Currency currency = account.getCurrency();
      if (currency != null)
      {
        currency.getAccountCollection().remove(account);
        currency = em.merge(currency);
      }
      User owner = account.getOwner();
      if (owner != null)
      {
        owner.getAccountCollection().remove(account);
        owner = em.merge(owner);
      }
      Collection<Movement> movementCollection = account.getMovementCollection();
      for (Movement movementCollectionMovement : movementCollection)
      {
        movementCollectionMovement.setOrigin(null);
        movementCollectionMovement = em.merge(movementCollectionMovement);
      }
      Collection<Movement> movementCollection1 = account.getMovementCollection1();
      for (Movement movementCollection1Movement : movementCollection1)
      {
        movementCollection1Movement.setDestination(null);
        movementCollection1Movement = em.merge(movementCollection1Movement);
      }
      em.remove(account);
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

  public List<Account> findAccountEntities()
  {
    return findAccountEntities(true, -1, -1);
  }

  public List<Account> findAccountEntities(int maxResults, int firstResult)
  {
    return findAccountEntities(false, maxResults, firstResult);
  }

  private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult)
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Account.class));
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

  public Account findAccount(Integer id)
  {
    EntityManager em = getEntityManager();
    try
    {
      return em.find(Account.class, id);
    }
    finally
    {
      em.close();
    }
  }

  public int getAccountCount()
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Account> rt = cq.from(Account.class);
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
