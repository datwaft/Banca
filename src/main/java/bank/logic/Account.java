package bank.logic;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "account")
@NamedQueries(
{
  @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
  @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
  @NamedQuery(name = "Account.findByAmount", query = "SELECT a FROM Account a WHERE a.amount = :amount"),
  @NamedQuery(name = "Account.findByDailylimit", query = "SELECT a FROM Account a WHERE a.dailylimit = :dailylimit")
})
public class Account implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "amount")
  private double amount;
  @Basic(optional = false)
  @Column(name = "dailylimit")
  private double dailylimit;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private Collection<Links> linksCollection;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "linkedAccount")
  private Collection<Links> linksCollection1;
  @OneToMany(mappedBy = "origin")
  private Collection<Movement> movementCollection;
  @OneToMany(mappedBy = "destination")
  private Collection<Movement> movementCollection1;
  @JoinColumn(name = "currency", referencedColumnName = "code")
  @ManyToOne(optional = false)
  private Currency currency;
  @JoinColumn(name = "owner", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private User owner;

  public Account()
  {
  }

  public Account(Integer id)
  {
    this.id = id;
  }

  public Account(Integer id, double amount, double dailylimit)
  {
    this.id = id;
    this.amount = amount;
    this.dailylimit = dailylimit;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public double getAmount()
  {
    return amount;
  }

  public void setAmount(double amount)
  {
    this.amount = amount;
  }

  public double getDailylimit()
  {
    return dailylimit;
  }

  public void setDailylimit(double dailylimit)
  {
    this.dailylimit = dailylimit;
  }

  public Collection<Links> getLinksCollection()
  {
    return linksCollection;
  }

  public void setLinksCollection(Collection<Links> linksCollection)
  {
    this.linksCollection = linksCollection;
  }

  public Collection<Links> getLinksCollection1()
  {
    return linksCollection1;
  }

  public void setLinksCollection1(Collection<Links> linksCollection1)
  {
    this.linksCollection1 = linksCollection1;
  }

  public Collection<Movement> getMovementCollection()
  {
    return movementCollection;
  }

  public void setMovementCollection(Collection<Movement> movementCollection)
  {
    this.movementCollection = movementCollection;
  }

  public Collection<Movement> getMovementCollection1()
  {
    return movementCollection1;
  }

  public void setMovementCollection1(Collection<Movement> movementCollection1)
  {
    this.movementCollection1 = movementCollection1;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public User getOwner()
  {
    return owner;
  }

  public void setOwner(User owner)
  {
    this.owner = owner;
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
    if (!(object instanceof Account))
    {
      return false;
    }
    Account other = (Account) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return "bank.logic.Account[ id=" + id + " ]";
  }

}
