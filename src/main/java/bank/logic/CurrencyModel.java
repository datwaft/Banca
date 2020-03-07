package bank.logic;

import bank.data.CurrencyDao;
import bank.data.exceptions.IllegalOrphanException;
import bank.data.exceptions.NonexistentEntityException;

public class CurrencyModel 
{
  CurrencyDao dao = CurrencyDao.getInstance();
  
  private CurrencyModel()
  {
    
  }

  public void create(Currency object) throws Exception
  {
    dao.create(object);
  }
  
  public void edit(Currency object) throws NonexistentEntityException, Exception
  {
    dao.edit(object);
  }
  
  public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException
  {
    dao.destroy(id);
  }
  
  public static CurrencyModel getInstance() 
  {
    return CurrencyModelHolder.INSTANCE;
  }

  private static class CurrencyModelHolder 
  {
    private static final CurrencyModel INSTANCE = new CurrencyModel();
  }
}
