package chapter09;

import chapter09.model.Supplier;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestNamedQuery extends TestBase{
  @Test
  public void testNamedQuery() {
    Query<Supplier> query = session.getNamedQuery("supplier.findAll");
    List<Supplier> suppliers = query.list();
    assertEquals(suppliers.size(), 2);
  }
}
