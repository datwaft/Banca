package bank.presentation.login;

import bank.logic.User;

public class Model
{
  User current;

  public Model()
  {
    this.setCurrent(new User());
  }

  public final User getCurrent()
  {
    return current;
  }

  public final void setCurrent(User current)
  {
    this.current = current;
  }
}
