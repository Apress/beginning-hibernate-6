package chapter09;

import chapter09.model.Product;
import chapter09.model.Supplier;
import org.hibernate.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

class ProductAndSupplier {
  final Product p;
  final Supplier s;

  ProductAndSupplier(Product p, Supplier s) {
    this.p = p;
    this.s = s;
  }

  @Override
  public String toString() {
    return "ProductAndSupplier{" +
        "p=" + p +
        ",\n   s=" + s +
        '}';
  }
}

public class TestJoinObject extends TestBase {
  @Test
  public void testJoinObject() {
    Query<ProductAndSupplier> query = session.createQuery(
        "select new chapter09.ProductAndSupplier(p,s) " +
            "from Product p, Supplier s where p.supplier=s",
        ProductAndSupplier.class);
    List<ProductAndSupplier> suppliers = query.list();
    for (ProductAndSupplier o : suppliers) {
      System.out.println(o);
    }
    assertEquals(suppliers.size(), 5);
  }
}
