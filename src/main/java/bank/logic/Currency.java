package bank.logic;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "currency")
@NamedQueries(
{
  @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c"),
  @NamedQuery(name = "Currency.findByCode", query = "SELECT c FROM Currency c WHERE c.code = :code"),
  @NamedQuery(name = "Currency.findByName", query = "SELECT c FROM Currency c WHERE c.name = :name"),
  @NamedQuery(name = "Currency.findByConversion", query = "SELECT c FROM Currency c WHERE c.conversion = :conversion"),
  @NamedQuery(name = "Currency.findByInterestRate", query = "SELECT c FROM Currency c WHERE c.interestRate = :interestRate")
})
public class Currency implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "code")
  private String code;
  @Basic(optional = false)
  @Column(name = "name")
  private String name;
  @Basic(optional = false)
  @Column(name = "conversion")
  private double conversion;
  @Basic(optional = false)
  @Column(name = "interest_rate")
  private double interestRate;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "currency")
  private Collection<Account> accountCollection;

  public Currency()
  {
  }

  public Currency(String code)
  {
    this.code = code;
  }

  public Currency(String code, String name, double conversion, double interestRate)
  {
    this.code = code;
    this.name = name;
    this.conversion = conversion;
    this.interestRate = interestRate;
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public double getConversion()
  {
    return conversion;
  }

  public void setConversion(double conversion)
  {
    this.conversion = conversion;
  }

  public double getInterestRate()
  {
    return interestRate;
  }

  public void setInterestRate(double interestRate)
  {
    this.interestRate = interestRate;
  }

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
    hash += (code != null ? code.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object)
  {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Currency))
    {
      return false;
    }
    Currency other = (Currency) object;
    if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code)))
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return "bank.logic.Currency[ code=" + code + " ]";
  }

}
