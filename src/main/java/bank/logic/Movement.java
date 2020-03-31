package bank.logic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "movement")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Movement.findAll", query = "SELECT m FROM Movement m"),
  @NamedQuery(name = "Movement.findById", query = "SELECT m FROM Movement m WHERE m.id = :id"),
  @NamedQuery(name = "Movement.findByAmount", query = "SELECT m FROM Movement m WHERE m.amount = :amount"),
  @NamedQuery(name = "Movement.findByDescription", query = "SELECT m FROM Movement m WHERE m.description = :description"),
  @NamedQuery(name = "Movement.findByDate", query = "SELECT m FROM Movement m WHERE m.date = :date")})
public class Movement implements Serializable {

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
  @Column(name = "description")
  private String description;
  @Basic(optional = false)
  @Column(name = "date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date date;
  @JoinColumn(name = "origin", referencedColumnName = "id")
  @ManyToOne
  private Account origin;
  @JoinColumn(name = "destination", referencedColumnName = "id")
  @ManyToOne
  private Account destination;

  public Movement() {
  }

  public Movement(Integer id) {
    this.id = id;
  }

  public Movement(Integer id, double amount, String description, Date date) {
    this.id = id;
    this.amount = amount;
    this.description = description;
    this.date = date;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Account getOrigin() {
    return origin;
  }

  public void setOrigin(Account origin) {
    this.origin = origin;
  }

  public Account getDestination() {
    return destination;
  }

  public void setDestination(Account destination) {
    this.destination = destination;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Movement)) {
      return false;
    }
    Movement other = (Movement) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "bank.logic.Movement[ id=" + id + " ]";
  }

}
