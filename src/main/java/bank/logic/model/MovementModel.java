package bank.logic.model;

import bank.data.MovementDao;
import bank.data.PersistenceManager;

public class MovementModel extends MovementDao {
  private MovementModel() {
    super(PersistenceManager.getInstance().getEntityManagerFactory());
  }
  
  public static MovementModel getInstance() {
    return MovementModelHolder.INSTANCE;
  }

  private static class MovementModelHolder {
    private static final MovementModel INSTANCE = new MovementModel();
  }
}
