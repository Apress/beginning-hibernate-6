package chapter09;

import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestNativeQuery extends TestBase {
  @Test
  public void testNativeQuery() {
    Query query = session.getNamedQuery("supplier.findAverage");
    List<Object[]> suppliers = query.list();
    for (Object[] o : suppliers) {
      System.out.println(Arrays.toString(o));
    }
    assertEquals(suppliers.size(), 2);
  }

  @Test
  public void testHSQLAggregate() {
    Query query = session.getNamedQuery("supplier.averagePrice");
    List<Object[]> suppliers = query.list();
    for (Object[] o : suppliers) {
      System.out.println(Arrays.toString(o));
    }
    assertEquals(suppliers.size(), 2);
  }

}

