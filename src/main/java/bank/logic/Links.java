package bank.logic;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "links")
@NamedQueries(
{
  @NamedQuery(name = "Links.findAll", query = "SELECT l FROM Links l"),
  @NamedQuery(name = "Links.findById", query = "SELECT l FROM Links l WHERE l.id = :id")
})
public class Links implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @JoinColumn(name = "owner", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Account owner;
  @JoinColumn(name = "linked_account", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Account linkedAccount;

  public Links()
  {
  }

  public Links(Integer id)
  {
    this.id = id;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public Account getOwner()
  {
    return owner;
  }

  public void setOwner(Account owner)
  {
    this.owner = owner;
  }

  public Account getLinkedAccount()
  {
    return linkedAccount;
  }

  public void setLinkedAccount(Account linkedAccount)
  {
    this.linkedAccount = linkedAccount;
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
    if (!(object instanceof Links))
    {
      return false;
    }
    Links other = (Links) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return "bank.logic.Links[ id=" + id + " ]";
  }

}
