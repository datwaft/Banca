package bank.data;

import bank.data.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bank.logic.Account;
import bank.logic.Link;
import java.util.List;
import javax.persistence.*;

public class LinkJpaController implements Serializable
{
  public LinkJpaController(EntityManagerFactory emf)
  {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager()
  {
    return emf.createEntityManager();
  }

  public void create(Link link)
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Account owner = link.getOwner();
      if (owner != null)
      {
        owner = em.getReference(owner.getClass(), owner.getId());
        link.setOwner(owner);
      }
      Account linkedAccount = link.getLinkedAccount();
      if (linkedAccount != null)
      {
        linkedAccount = em.getReference(linkedAccount.getClass(), linkedAccount.getId());
        link.setLinkedAccount(linkedAccount);
      }
      em.persist(link);
      if (owner != null)
      {
        owner.getLinkCollection().add(link);
        owner = em.merge(owner);
      }
      if (linkedAccount != null)
      {
        linkedAccount.getLinkCollection().add(link);
        linkedAccount = em.merge(linkedAccount);
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

  public void edit(Link link) throws NonexistentEntityException, Exception
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Link persistentLink = em.find(Link.class, link.getId());
      Account ownerOld = persistentLink.getOwner();
      Account ownerNew = link.getOwner();
      Account linkedAccountOld = persistentLink.getLinkedAccount();
      Account linkedAccountNew = link.getLinkedAccount();
      if (ownerNew != null)
      {
        ownerNew = em.getReference(ownerNew.getClass(), ownerNew.getId());
        link.setOwner(ownerNew);
      }
      if (linkedAccountNew != null)
      {
        linkedAccountNew = em.getReference(linkedAccountNew.getClass(), linkedAccountNew.getId());
        link.setLinkedAccount(linkedAccountNew);
      }
      link = em.merge(link);
      if (ownerOld != null && !ownerOld.equals(ownerNew))
      {
        ownerOld.getLinkCollection().remove(link);
        ownerOld = em.merge(ownerOld);
      }
      if (ownerNew != null && !ownerNew.equals(ownerOld))
      {
        ownerNew.getLinkCollection().add(link);
        ownerNew = em.merge(ownerNew);
      }
      if (linkedAccountOld != null && !linkedAccountOld.equals(linkedAccountNew))
      {
        linkedAccountOld.getLinkCollection().remove(link);
        linkedAccountOld = em.merge(linkedAccountOld);
      }
      if (linkedAccountNew != null && !linkedAccountNew.equals(linkedAccountOld))
      {
        linkedAccountNew.getLinkCollection().add(link);
        linkedAccountNew = em.merge(linkedAccountNew);
      }
      em.getTransaction().commit();
    }
    catch (Exception ex)
    {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0)
      {
        Integer id = link.getId();
        if (findLink(id) == null)
        {
          throw new NonexistentEntityException("The link with id " + id + " no longer exists.");
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

  public void destroy(Integer id) throws NonexistentEntityException
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Link link;
      try
      {
        link = em.getReference(Link.class, id);
        link.getId();
      }
      catch (EntityNotFoundException enfe)
      {
        throw new NonexistentEntityException("The link with id " + id + " no longer exists.", enfe);
      }
      Account owner = link.getOwner();
      if (owner != null)
      {
        owner.getLinkCollection().remove(link);
        owner = em.merge(owner);
      }
      Account linkedAccount = link.getLinkedAccount();
      if (linkedAccount != null)
      {
        linkedAccount.getLinkCollection().remove(link);
        linkedAccount = em.merge(linkedAccount);
      }
      em.remove(link);
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

  public List<Link> findLinkEntities()
  {
    return findLinkEntities(true, -1, -1);
  }

  public List<Link> findLinkEntities(int maxResults, int firstResult)
  {
    return findLinkEntities(false, maxResults, firstResult);
  }

  private List<Link> findLinkEntities(boolean all, int maxResults, int firstResult)
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Link.class));
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

  public Link findLink(Integer id)
  {
    EntityManager em = getEntityManager();
    try
    {
      return em.find(Link.class, id);
    }
    finally
    {
      em.close();
    }
  }

  public int getLinkCount()
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Link> rt = cq.from(Link.class);
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
