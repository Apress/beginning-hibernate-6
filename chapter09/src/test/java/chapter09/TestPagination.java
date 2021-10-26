package chapter09;

import chapter09.model.Supplier;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class TestPagination {
  @Test
  public void testPagination() {
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      session.createQuery("delete from Product").executeUpdate();
      session.createQuery("delete from Supplier").executeUpdate();

      for (int i = 0; i < 30; i++) {
        Supplier supplier = new Supplier();
        supplier.setName(String.format("supplier %02d", i));
        session.save(supplier);
      }

      tx.commit();
    }

    try (Session session = SessionUtil.getSession()) {
      Query<String> query = session.createQuery(
          "select s.name from Supplier s order by s.name",
          String.class);
      query.setFirstResult(4);
      query.setMaxResults(4);
      List<String> suppliers = query.list();
      String list = suppliers
          .stream()
          .collect(Collectors.joining(","));
      assertEquals(list,
          "supplier 04,supplier 05,supplier 06,supplier 07");
    }
  }
}
