package bank.logic;

import bank.data.AccountDao;
import bank.data.exceptions.IllegalOrphanException;
import bank.data.exceptions.NonexistentEntityException;

public class AccountModel 
{
  AccountDao dao = AccountDao.getInstance();
  
  private AccountModel()
  {
    
  }

  public void create(Account object) throws Exception
  {
    dao.create(object);
  }
  
  public void edit(Account object) throws NonexistentEntityException, Exception
  {
    dao.edit(object);
  }
  
  public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
  {
    dao.destroy(id);
  }
  
  public static AccountModel getInstance() 
  {
    return AccountModelHolder.INSTANCE;
  }

  private static class AccountModelHolder 
  {
    private static final AccountModel INSTANCE = new AccountModel();
  }
}
