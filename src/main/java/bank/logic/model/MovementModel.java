package bank.logic.model;

import bank.data.MovementDao;

public class MovementModel extends MovementDao {
  private MovementModel() { }
  
  public static MovementModel getInstance() {
    return MovementModelHolder.INSTANCE;
  }

  private static class MovementModelHolder {
    private static final MovementModel INSTANCE = new MovementModel();
  }
}
