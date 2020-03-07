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
    if (account.getLinkCollection() == null)
    {
      account.setLinkCollection(new ArrayList<Link>());
    }
    if (account.getLinkCollection1() == null)
    {
      account.setLinkCollection1(new ArrayList<Link>());
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
      Collection<Link> attachedLinkCollection = new ArrayList<Link>();
      for (Link linkCollectionLinkToAttach : account.getLinkCollection())
      {
        linkCollectionLinkToAttach = em.getReference(linkCollectionLinkToAttach.getClass(), linkCollectionLinkToAttach.getId());
        attachedLinkCollection.add(linkCollectionLinkToAttach);
      }
      account.setLinkCollection(attachedLinkCollection);
      Collection<Link> attachedLinkCollection1 = new ArrayList<Link>();
      for (Link linkCollection1LinkToAttach : account.getLinkCollection1())
      {
        linkCollection1LinkToAttach = em.getReference(linkCollection1LinkToAttach.getClass(), linkCollection1LinkToAttach.getId());
        attachedLinkCollection1.add(linkCollection1LinkToAttach);
      }
      account.setLinkCollection1(attachedLinkCollection1);
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
      for (Link linkCollectionLink : account.getLinkCollection())
      {
        Account oldOwnerOfLinkCollectionLink = linkCollectionLink.getOwner();
        linkCollectionLink.setOwner(account);
        linkCollectionLink = em.merge(linkCollectionLink);
        if (oldOwnerOfLinkCollectionLink != null)
        {
          oldOwnerOfLinkCollectionLink.getLinkCollection().remove(linkCollectionLink);
          oldOwnerOfLinkCollectionLink = em.merge(oldOwnerOfLinkCollectionLink);
        }
      }
      for (Link linkCollection1Link : account.getLinkCollection1())
      {
        Account oldLinkedAccountOfLinkCollection1Link = linkCollection1Link.getLinkedAccount();
        linkCollection1Link.setLinkedAccount(account);
        linkCollection1Link = em.merge(linkCollection1Link);
        if (oldLinkedAccountOfLinkCollection1Link != null)
        {
          oldLinkedAccountOfLinkCollection1Link.getLinkCollection1().remove(linkCollection1Link);
          oldLinkedAccountOfLinkCollection1Link = em.merge(oldLinkedAccountOfLinkCollection1Link);
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
      Collection<Link> linkCollectionOld = persistentAccount.getLinkCollection();
      Collection<Link> linkCollectionNew = account.getLinkCollection();
      Collection<Link> linkCollection1Old = persistentAccount.getLinkCollection1();
      Collection<Link> linkCollection1New = account.getLinkCollection1();
      Collection<Movement> movementCollectionOld = persistentAccount.getMovementCollection();
      Collection<Movement> movementCollectionNew = account.getMovementCollection();
      Collection<Movement> movementCollection1Old = persistentAccount.getMovementCollection1();
      Collection<Movement> movementCollection1New = account.getMovementCollection1();
      List<String> illegalOrphanMessages = null;
      for (Link linkCollectionOldLink : linkCollectionOld)
      {
        if (!linkCollectionNew.contains(linkCollectionOldLink))
        {
          if (illegalOrphanMessages == null)
          {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Link " + linkCollectionOldLink + " since its owner field is not nullable.");
        }
      }
      for (Link linkCollection1OldLink : linkCollection1Old)
      {
        if (!linkCollection1New.contains(linkCollection1OldLink))
        {
          if (illegalOrphanMessages == null)
          {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Link " + linkCollection1OldLink + " since its linkedAccount field is not nullable.");
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
      Collection<Link> attachedLinkCollectionNew = new ArrayList<Link>();
      for (Link linkCollectionNewLinkToAttach : linkCollectionNew)
      {
        linkCollectionNewLinkToAttach = em.getReference(linkCollectionNewLinkToAttach.getClass(), linkCollectionNewLinkToAttach.getId());
        attachedLinkCollectionNew.add(linkCollectionNewLinkToAttach);
      }
      linkCollectionNew = attachedLinkCollectionNew;
      account.setLinkCollection(linkCollectionNew);
      Collection<Link> attachedLinkCollection1New = new ArrayList<Link>();
      for (Link linkCollection1NewLinkToAttach : linkCollection1New)
      {
        linkCollection1NewLinkToAttach = em.getReference(linkCollection1NewLinkToAttach.getClass(), linkCollection1NewLinkToAttach.getId());
        attachedLinkCollection1New.add(linkCollection1NewLinkToAttach);
      }
      linkCollection1New = attachedLinkCollection1New;
      account.setLinkCollection1(linkCollection1New);
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
      for (Link linkCollectionNewLink : linkCollectionNew)
      {
        if (!linkCollectionOld.contains(linkCollectionNewLink))
        {
          Account oldOwnerOfLinkCollectionNewLink = linkCollectionNewLink.getOwner();
          linkCollectionNewLink.setOwner(account);
          linkCollectionNewLink = em.merge(linkCollectionNewLink);
          if (oldOwnerOfLinkCollectionNewLink != null && !oldOwnerOfLinkCollectionNewLink.equals(account))
          {
            oldOwnerOfLinkCollectionNewLink.getLinkCollection().remove(linkCollectionNewLink);
            oldOwnerOfLinkCollectionNewLink = em.merge(oldOwnerOfLinkCollectionNewLink);
          }
        }
      }
      for (Link linkCollection1NewLink : linkCollection1New)
      {
        if (!linkCollection1Old.contains(linkCollection1NewLink))
        {
          Account oldLinkedAccountOfLinkCollection1NewLink = linkCollection1NewLink.getLinkedAccount();
          linkCollection1NewLink.setLinkedAccount(account);
          linkCollection1NewLink = em.merge(linkCollection1NewLink);
          if (oldLinkedAccountOfLinkCollection1NewLink != null && !oldLinkedAccountOfLinkCollection1NewLink.equals(account))
          {
            oldLinkedAccountOfLinkCollection1NewLink.getLinkCollection1().remove(linkCollection1NewLink);
            oldLinkedAccountOfLinkCollection1NewLink = em.merge(oldLinkedAccountOfLinkCollection1NewLink);
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
      Collection<Link> linkCollectionOrphanCheck = account.getLinkCollection();
      for (Link linkCollectionOrphanCheckLink : linkCollectionOrphanCheck)
      {
        if (illegalOrphanMessages == null)
        {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Link " + linkCollectionOrphanCheckLink + " in its linkCollection field has a non-nullable owner field.");
      }
      Collection<Link> linkCollection1OrphanCheck = account.getLinkCollection1();
      for (Link linkCollection1OrphanCheckLink : linkCollection1OrphanCheck)
      {
        if (illegalOrphanMessages == null)
        {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Link " + linkCollection1OrphanCheckLink + " in its linkCollection1 field has a non-nullable linkedAccount field.");
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
