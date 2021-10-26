//tag::preamble[]
package chapter09.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
//end::preamble[]
//tag::queries[]
@NamedQueries({
    @NamedQuery(name = "supplier.findAll",
        query = "from Supplier s"),
    @NamedQuery(name = "supplier.findByName",
        query = "from Supplier s where s.name=:name"),
    @NamedQuery(name = "supplier.averagePrice",
        query = "select p.supplier.id, avg(p.price) " +
            "from Product p " +
            "GROUP BY p.supplier.id"),
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "supplier.findAverage",
        query = "SELECT p.supplier_id, avg(p.price) "
            + "FROM Product p GROUP BY p.supplier_id"
    )
})
//end::queries[]
//tag::source[]
public class Supplier implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;
  @Column(unique = true)
  @NotNull
  String name;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
      mappedBy = "supplier", targetEntity = Product.class)
  List<Product> products = new ArrayList<>();

  public Supplier(String name) {
    this.name = name;
  }

  public Supplier() {
  }
  //end::source[]

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Supplier)) return false;

    Supplier supplier = (Supplier) o;

    if (id != null ? !id.equals(supplier.id) : supplier.id != null)
      return false;
    return name.equals(supplier.name);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + name.hashCode();
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Supplier{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
