//tag::preamble[]
package chapter09.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Software extends Product implements Serializable {
  @Column
  @NotNull
  String version;

  public Software() {
  }

  public Software(Supplier supplier,
                  String name,
                  String description,
                  Double price,
                  String version) {
    super(supplier, name, description, price);
    this.version = version;
  }
  //end::preamble[]

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Software)) return false;
    if (!super.equals(o)) return false;
    Software software = (Software) o;
    return getVersion().equals(software.getVersion());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getVersion());
  }

  @Override
  public String toString() {
    return "Software{" +
        "id=" + id +
        ", supplier=" + supplier +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", version='" + version + '\'' +
        '}';
  }
}
