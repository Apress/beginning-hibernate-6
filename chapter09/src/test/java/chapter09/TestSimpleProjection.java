package chapter09;

import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestSimpleProjection extends TestBase {
  @Test
  public void testSimpleProjection() {
    Query<String> query = session.createQuery(
        "select p.name from Product p",
        String.class);
    List<String> suppliers = query.list();
    for (String s : suppliers) {
      System.out.println(s);
    }
    assertEquals(suppliers.size(), 5);
  }
}
