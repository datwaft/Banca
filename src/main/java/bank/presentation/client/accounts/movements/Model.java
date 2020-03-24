package bank.presentation.client.accounts.movements;

import bank.logic.Movement;
import java.util.ArrayList;
import java.util.List;

public final class Model {
  List<Movement> movements;
  
  public Model() {
    this.setMovements(new ArrayList<>());
  }

  public List<Movement> getMovements() {
    return movements;
  }

  public void setMovements(List<Movement> movements) {
    this.movements = movements;
  }
}
