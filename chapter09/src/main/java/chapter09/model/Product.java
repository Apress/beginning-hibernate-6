//tag::preamble[]
package chapter09.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//end::preamble[]
//tag::queries[]
@NamedQueries({
    @NamedQuery(name = "product.searchByPhrase",
        query = "from Product p "
            + "where p.name like :text or p.description like :text"),
    @NamedQuery(name = "product.findProductAndSupplier",
        query = "from Product p, Supplier s where p.supplier=s"),
})
//end::queries[]
//tag::source[]
public class Product implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  Supplier supplier;
  @Column
  @NotNull
  String name;
  @Column
  @NotNull
  String description;
  @Column
  @NotNull
  Double price;

  public Product() {
  }

  public Product(Supplier supplier,
                 String name,
                 String description,
                 Double price) {
    this.supplier = supplier;
    this.name = name;
    this.description = description;
    this.price = price;
  }
  //end::source[]

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product)) return false;
    Product product = (Product) o;
    return Objects.equals(getId(), product.getId()) &&
        Objects.equals(getSupplier(), product.getSupplier()) &&
        Objects.equals(getName(), product.getName()) &&
        Objects.equals(getDescription(), product.getDescription()) &&
        Objects.equals(getPrice(), product.getPrice());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getSupplier(),
        getName(),
        getDescription(),
        getPrice());
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", supplier='" + getSupplier() + '\'' +
        ", price=" + price +
        '}';
  }
}
