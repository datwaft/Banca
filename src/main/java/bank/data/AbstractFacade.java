package bank.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public abstract class AbstractFacade<T> {
  private final Class<T> entityClass;

  public AbstractFacade() {
      this.entityClass = null;
  }

  public AbstractFacade(Class<T> entityClass) {
      this.entityClass = entityClass;
  }

  protected abstract EntityManager getEntityManager();

  public void persist(T entity) throws PersistenceException {
    EntityManager em = getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(entity);
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }

  public void merge(T entity) throws PersistenceException {
    EntityManager em = getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(entity);
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }

  public void remove(T entity) throws PersistenceException {
    EntityManager em = getEntityManager();
    try {
      em.getTransaction().begin();
      em.remove(em.merge(entity));
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }

  public T find(Object id) throws PersistenceException {
    EntityManager em = getEntityManager();
    try {
      return em.find(entityClass, id);
    } finally {
      em.close();
    }
  }

  public List<T> findAll() throws PersistenceException {
    EntityManager em = getEntityManager();
    try {
      javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(entityClass));
      return em.createQuery(cq).getResultList();
    } finally {
      em.close();
    }
  }

  public void refresh() throws PersistenceException {
    EntityManager em = getEntityManager();
    try {
      em.getEntityManagerFactory().getCache().evictAll();
    } finally {
      em.close();
    }
  }
}