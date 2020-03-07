package bank.data;

import bank.data.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bank.logic.Account;
import bank.logic.Links;
import java.util.List;
import javax.persistence.*;

public class LinksJpaController implements Serializable
{
  public LinksJpaController(EntityManagerFactory emf)
  {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager()
  {
    return emf.createEntityManager();
  }

  public void create(Links links)
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Account owner = links.getOwner();
      if (owner != null)
      {
        owner = em.getReference(owner.getClass(), owner.getId());
        links.setOwner(owner);
      }
      Account linkedAccount = links.getLinkedAccount();
      if (linkedAccount != null)
      {
        linkedAccount = em.getReference(linkedAccount.getClass(), linkedAccount.getId());
        links.setLinkedAccount(linkedAccount);
      }
      em.persist(links);
      if (owner != null)
      {
        owner.getLinksCollection().add(links);
        owner = em.merge(owner);
      }
      if (linkedAccount != null)
      {
        linkedAccount.getLinksCollection().add(links);
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

  public void edit(Links links) throws NonexistentEntityException, Exception
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Links persistentLinks = em.find(Links.class, links.getId());
      Account ownerOld = persistentLinks.getOwner();
      Account ownerNew = links.getOwner();
      Account linkedAccountOld = persistentLinks.getLinkedAccount();
      Account linkedAccountNew = links.getLinkedAccount();
      if (ownerNew != null)
      {
        ownerNew = em.getReference(ownerNew.getClass(), ownerNew.getId());
        links.setOwner(ownerNew);
      }
      if (linkedAccountNew != null)
      {
        linkedAccountNew = em.getReference(linkedAccountNew.getClass(), linkedAccountNew.getId());
        links.setLinkedAccount(linkedAccountNew);
      }
      links = em.merge(links);
      if (ownerOld != null && !ownerOld.equals(ownerNew))
      {
        ownerOld.getLinksCollection().remove(links);
        ownerOld = em.merge(ownerOld);
      }
      if (ownerNew != null && !ownerNew.equals(ownerOld))
      {
        ownerNew.getLinksCollection().add(links);
        ownerNew = em.merge(ownerNew);
      }
      if (linkedAccountOld != null && !linkedAccountOld.equals(linkedAccountNew))
      {
        linkedAccountOld.getLinksCollection().remove(links);
        linkedAccountOld = em.merge(linkedAccountOld);
      }
      if (linkedAccountNew != null && !linkedAccountNew.equals(linkedAccountOld))
      {
        linkedAccountNew.getLinksCollection().add(links);
        linkedAccountNew = em.merge(linkedAccountNew);
      }
      em.getTransaction().commit();
    }
    catch (Exception ex)
    {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0)
      {
        Integer id = links.getId();
        if (findLinks(id) == null)
        {
          throw new NonexistentEntityException("The links with id " + id + " no longer exists.");
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
      Links links;
      try
      {
        links = em.getReference(Links.class, id);
        links.getId();
      }
      catch (EntityNotFoundException enfe)
      {
        throw new NonexistentEntityException("The links with id " + id + " no longer exists.", enfe);
      }
      Account owner = links.getOwner();
      if (owner != null)
      {
        owner.getLinksCollection().remove(links);
        owner = em.merge(owner);
      }
      Account linkedAccount = links.getLinkedAccount();
      if (linkedAccount != null)
      {
        linkedAccount.getLinksCollection().remove(links);
        linkedAccount = em.merge(linkedAccount);
      }
      em.remove(links);
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

  public List<Links> findLinksEntities()
  {
    return findLinksEntities(true, -1, -1);
  }

  public List<Links> findLinksEntities(int maxResults, int firstResult)
  {
    return findLinksEntities(false, maxResults, firstResult);
  }

  private List<Links> findLinksEntities(boolean all, int maxResults, int firstResult)
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Links.class));
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

  public Links findLinks(Integer id)
  {
    EntityManager em = getEntityManager();
    try
    {
      return em.find(Links.class, id);
    }
    finally
    {
      em.close();
    }
  }

  public int getLinksCount()
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Links> rt = cq.from(Links.class);
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
