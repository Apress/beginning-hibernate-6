package chapter09;

import chapter09.model.Product;
import chapter09.model.Supplier;
import org.hibernate.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestJoinArray extends TestBase {
  @Test
  public void testJoinArray() {
    Query<Object[]> query = session.getNamedQuery(
        "product.findProductAndSupplier"
    );
    List<Object[]> suppliers = query.list();
    for (Object[] o : suppliers) {
      Assert.assertTrue(o[0] instanceof Product);
      Assert.assertTrue(o[1] instanceof Supplier);
    }
    assertEquals(suppliers.size(), 5);
  }
}
