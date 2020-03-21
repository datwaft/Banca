package bank.logic;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries(
{
  @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
  @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
  @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
  @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
  @NamedQuery(name = "User.findByCellphone", query = "SELECT u FROM User u WHERE u.cellphone = :cellphone"),
  @NamedQuery(name = "User.findByCashier", query = "SELECT u FROM User u WHERE u.cashier = :cashier"),
  @NamedQuery(name = "User.findByClient", query = "SELECT u FROM User u WHERE u.client = :client")
})
public class User implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private String id;
  @Basic(optional = false)
  @Column(name = "password")
  private String password;
  @Basic(optional = false)
  @Column(name = "name")
  private String name;
  @Basic(optional = false)
  @Column(name = "cellphone")
  private String cellphone;
  @Basic(optional = false)
  @Column(name = "cashier")
  private boolean cashier;
  @Basic(optional = false)
  @Column(name = "client")
  private boolean client;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private Collection<Account> accountCollection;

  public User()
  {
  }

  public User(String id)
  {
    this.id = id;
  }

  public User(String id, String password, String name, String cellphone, boolean cashier, boolean client)
  {
    this.id = id;
    this.password = password;
    this.name = name;
    this.cellphone = cellphone;
    this.cashier = cashier;
    this.client = client;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getCellphone()
  {
    return cellphone;
  }

  public void setCellphone(String cellphone)
  {
    this.cellphone = cellphone;
  }

  public boolean getCashier()
  {
    return cashier;
  }

  public void setCashier(boolean cashier)
  {
    this.cashier = cashier;
  }

  public boolean getClient()
  {
    return client;
  }

  public void setClient(boolean client)
  {
    this.client = client;
  }

  @XmlTransient
  public Collection<Account> getAccountCollection()
  {
    return accountCollection;
  }

  public void setAccountCollection(Collection<Account> accountCollection)
  {
    this.accountCollection = accountCollection;
  }

  @Override
  public int hashCode()
  {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object)
  {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof User))
    {
      return false;
    }
    User other = (User) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return "bank.logic.User[ id=" + id + " ]";
  }

}
