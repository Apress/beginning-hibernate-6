package chapter09;

import chapter09.model.Product;
import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import org.testng.annotations.Test;


public class TestSingleResult extends TestBase {
  @Test(expectedExceptions = NonUniqueResultException.class)
  public void testGetSingleResultBad() {
    Query<Product> query = session.createQuery(
        "from Product",
        Product.class);

    Product products = query.getSingleResult();
  }

  @Test
  public void testGetSingleResultGood() {
    Query<Product> query = session.createQuery(
        "from Product",
        Product.class);
    query.setMaxResults(1);
    Product products = query.getSingleResult();
  }
}
