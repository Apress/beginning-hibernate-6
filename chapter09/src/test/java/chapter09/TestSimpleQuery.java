package chapter09;

import chapter09.model.Product;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestSimpleQuery extends TestBase{
  @Test
  public void testSimpleQuery() {
    Query<Product> query = session.createQuery(
        "from Product",
        Product.class);

    query.setComment("This is only a query for product");
    List<Product> products = query.list();
    assertEquals(products.size(), 5);
  }
}
