//tag::preamble[]
package chapter06.naturalid;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class SimpleNaturalIdEmployee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;
  @NaturalId
  Integer badge;
  String name;
  @Column(scale=2,precision=5,nullable=false)
  double royalty;

  public SimpleNaturalIdEmployee() {
  }
  //end::preamble[]

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getBadge() {
    return badge;
  }

  public void setBadge(Integer badge) {
    this.badge = badge;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getRoyalty() {
    return royalty;
  }

  public void setRoyalty(double royalty) {
    this.royalty = royalty;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SimpleNaturalIdEmployee)) return false;

    SimpleNaturalIdEmployee that = (SimpleNaturalIdEmployee) o;

    if (badge != null ? !badge.equals(that.badge) : that.badge != null) return false;
    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (badge != null ? badge.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SimpleNaturalIdEmployee{" +
      "id=" + id +
      ", badge=" + badge +
      ", name='" + name + '\'' +
      ", royalty=" + royalty +
      '}';
  }
}
