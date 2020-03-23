package bank.logic.model;

import bank.data.LinkDao;

public class LinkModel extends LinkDao {
  private LinkModel() { }
  
  public static LinkModel getInstance() {
    return LinkModelHolder.INSTANCE;
  }

  private static class LinkModelHolder {
    private static final LinkModel INSTANCE = new LinkModel();
  }
}
