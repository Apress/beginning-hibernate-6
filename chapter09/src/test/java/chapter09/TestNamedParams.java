package chapter09;

import chapter09.model.Product;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestNamedParams extends TestBase {
  @Test
  public void testNamedParams() {
    Query<Product> query = session.createQuery(
        "from Product where price >= :price",
        Product.class);
    query.setParameter("price",25.0);
    List<Product> products = query.list();
    assertEquals(products.size(), 1);
  }
}
