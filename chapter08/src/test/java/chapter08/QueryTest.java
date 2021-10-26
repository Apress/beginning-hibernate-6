package chapter08;

import chapter08.model.Supplier;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class QueryTest {
  List<Integer> keys = new ArrayList<>();

  @BeforeMethod
  public void populateData() {
    clearSuppliers();
    Session session = SessionUtil.getSession();
    Transaction tx = session.beginTransaction();
    for (int i = 0; i < 10; i++) {
      Supplier supplier = new Supplier("Supplier " + (i + 1));
      session.save(supplier);
      keys.add(supplier.getId());
    }
    tx.commit();
    session.close();
  }

  @AfterMethod
  public void clearSuppliers() {
    Session session = SessionUtil.getSession();
    Transaction tx = session.beginTransaction();

    session.createQuery("delete from Supplier")
        .executeUpdate();
    tx.commit();
    session.close();
  }

  @Test
  public void testSuppliers() {
    for(int i=0;i<100; i++) {
      // create a new Session every loop...
      try(Session session=SessionUtil.getSession()) {
        Transaction tx = session.beginTransaction();
        Integer key=keys.get((int)(Math.random()*keys.size()));
        Supplier supplier = session.get(Supplier.class,key);
        System.out.println(supplier.getName());
        tx.commit();
      }
    }
  }
}
