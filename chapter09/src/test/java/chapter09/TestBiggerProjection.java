package chapter09;

import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestBiggerProjection extends TestBase {
  @Test
  public void testBiggerProjection() {
    Query<Object[]> query = session.createQuery(
        "select p.name, p.price from Product p");
    List<Object[]> products = query.list();
    for (Object[] data : products) {
      System.out.println(Arrays.toString(data));
    }
    assertEquals(products.size(), 5);
  }
}
