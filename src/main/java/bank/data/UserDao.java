package bank.data;

import javax.persistence.EntityManager;
import bank.logic.User;
import java.util.Collection;

public class UserDao extends UserJpaController
{
  private UserDao()
  {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }

  public static UserDao getInstance() 
  {
    return AccountDaoHolder.INSTANCE;
  }

  public Collection<User> verifyUser(String id, String password)
  {
    EntityManager em = getEntityManager();
    try
    {
      return em.createQuery("SELECT o FROM user o WHERE o.id = :id AND o.password = :password")
        .setParameter("id", id)
        .setParameter("password", password)
        .getResultList();
    }
    finally
    {
      em.close();
    }
  }

  private static class AccountDaoHolder 
  {
    private static final UserDao INSTANCE = new UserDao();
  }
}
