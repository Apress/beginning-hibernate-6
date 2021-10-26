package chapter09;

import chapter09.model.Product;
import chapter09.model.Supplier;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class TestNamedEntity extends TestBase {
  @Test
  public void testNamedEntity() {
    Query<Supplier> supplierQuery=session.createQuery(
            "from Supplier where name=:name",
        Supplier.class);
    supplierQuery.setParameter("name", "Supplier 2");
    Supplier supplier= supplierQuery.getSingleResult();
    assertNotNull(supplier);

    Query<Product> query = session.createQuery(
        "from Product where supplier = :supplier",
        Product.class);
    query.setParameter("supplier", supplier);

    List<Product> products = query.list();
    assertEquals(products.size(), 3);
  }
}
