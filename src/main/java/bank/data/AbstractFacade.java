package bank.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public abstract class AbstractFacade<T>
{
  private final Class<T> entityClass;

  public AbstractFacade()
  {
      this.entityClass = null;
  }

  public AbstractFacade(Class<T> entityClass) {
      this.entityClass = entityClass;
  }

  protected abstract EntityManager getEntityManager();

  public void persist(T entity) throws PersistenceException
  {
    EntityManager entitymanager = getEntityManager();
    entitymanager.getTransaction( ).begin( );
    entitymanager.persist(entity);
    entitymanager.getTransaction().commit();
    entitymanager.close();
  }

  public void merge(T entity) throws PersistenceException
  {
    EntityManager entitymanager = getEntityManager();
    entitymanager.getTransaction( ).begin( );
    entitymanager.merge(entity);
    entitymanager.getTransaction().commit();
    entitymanager.close();
  }

  public void remove(T entity) throws PersistenceException
  {
    EntityManager entitymanager = getEntityManager();
    entitymanager.getTransaction( ).begin( );
    entitymanager.remove(entitymanager.merge(entity));
    entitymanager.getTransaction().commit();
    entitymanager.close();
  }

  public T find(Object id) throws PersistenceException
  {
    return getEntityManager().find(entityClass, id);
  }

  public List<T> findAll() throws PersistenceException
  {
    javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    cq.select(cq.from(entityClass));
    return getEntityManager().createQuery(cq).getResultList();
  }

  public void refresh() throws PersistenceException
  {
    getEntityManager().getEntityManagerFactory().getCache().evictAll();
  }
}