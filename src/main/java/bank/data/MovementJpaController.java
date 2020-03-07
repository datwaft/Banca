package bank.data;

import bank.data.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bank.logic.Account;
import bank.logic.Movement;
import java.util.List;
import javax.persistence.*;

public class MovementJpaController implements Serializable
{
  public MovementJpaController(EntityManagerFactory emf)
  {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager()
  {
    return emf.createEntityManager();
  }

  public void create(Movement movement)
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Account origin = movement.getOrigin();
      if (origin != null)
      {
        origin = em.getReference(origin.getClass(), origin.getId());
        movement.setOrigin(origin);
      }
      Account destination = movement.getDestination();
      if (destination != null)
      {
        destination = em.getReference(destination.getClass(), destination.getId());
        movement.setDestination(destination);
      }
      em.persist(movement);
      if (origin != null)
      {
        origin.getMovementCollection().add(movement);
        origin = em.merge(origin);
      }
      if (destination != null)
      {
        destination.getMovementCollection().add(movement);
        destination = em.merge(destination);
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

  public void edit(Movement movement) throws NonexistentEntityException, Exception
  {
    EntityManager em = null;
    try
    {
      em = getEntityManager();
      em.getTransaction().begin();
      Movement persistentMovement = em.find(Movement.class, movement.getId());
      Account originOld = persistentMovement.getOrigin();
      Account originNew = movement.getOrigin();
      Account destinationOld = persistentMovement.getDestination();
      Account destinationNew = movement.getDestination();
      if (originNew != null)
      {
        originNew = em.getReference(originNew.getClass(), originNew.getId());
        movement.setOrigin(originNew);
      }
      if (destinationNew != null)
      {
        destinationNew = em.getReference(destinationNew.getClass(), destinationNew.getId());
        movement.setDestination(destinationNew);
      }
      movement = em.merge(movement);
      if (originOld != null && !originOld.equals(originNew))
      {
        originOld.getMovementCollection().remove(movement);
        originOld = em.merge(originOld);
      }
      if (originNew != null && !originNew.equals(originOld))
      {
        originNew.getMovementCollection().add(movement);
        originNew = em.merge(originNew);
      }
      if (destinationOld != null && !destinationOld.equals(destinationNew))
      {
        destinationOld.getMovementCollection().remove(movement);
        destinationOld = em.merge(destinationOld);
      }
      if (destinationNew != null && !destinationNew.equals(destinationOld))
      {
        destinationNew.getMovementCollection().add(movement);
        destinationNew = em.merge(destinationNew);
      }
      em.getTransaction().commit();
    }
    catch (Exception ex)
    {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0)
      {
        Integer id = movement.getId();
        if (findMovement(id) == null)
        {
          throw new NonexistentEntityException("The movement with id " + id + " no longer exists.");
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
      Movement movement;
      try
      {
        movement = em.getReference(Movement.class, id);
        movement.getId();
      }
      catch (EntityNotFoundException enfe)
      {
        throw new NonexistentEntityException("The movement with id " + id + " no longer exists.", enfe);
      }
      Account origin = movement.getOrigin();
      if (origin != null)
      {
        origin.getMovementCollection().remove(movement);
        origin = em.merge(origin);
      }
      Account destination = movement.getDestination();
      if (destination != null)
      {
        destination.getMovementCollection().remove(movement);
        destination = em.merge(destination);
      }
      em.remove(movement);
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

  public List<Movement> findMovementEntities()
  {
    return findMovementEntities(true, -1, -1);
  }

  public List<Movement> findMovementEntities(int maxResults, int firstResult)
  {
    return findMovementEntities(false, maxResults, firstResult);
  }

  private List<Movement> findMovementEntities(boolean all, int maxResults, int firstResult)
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Movement.class));
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

  public Movement findMovement(Integer id)
  {
    EntityManager em = getEntityManager();
    try
    {
      return em.find(Movement.class, id);
    }
    finally
    {
      em.close();
    }
  }

  public int getMovementCount()
  {
    EntityManager em = getEntityManager();
    try
    {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Movement> rt = cq.from(Movement.class);
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
