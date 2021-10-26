package chapter09;

import chapter09.model.Product;
import chapter09.model.Software;
import chapter09.model.Supplier;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {
  Session session;
  Transaction tx;

  @BeforeMethod
  public void populateData() {
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      Supplier supplier = new Supplier("Hardware, Inc.");
      supplier.getProducts().add(
          new Product(supplier, "Optical Wheel Mouse", "Mouse", 5.00));
      supplier.getProducts().add(
          new Product(supplier, "Trackball Mouse", "Mouse", 22.00));
      session.save(supplier);

      supplier = new Supplier("Supplier 2");
      supplier.getProducts().add(
          new Software(supplier, "SuperDetect", "Antivirus", 14.95, "1.0"));
      supplier.getProducts().add(
          new Software(supplier, "Wildcat", "Browser", 19.95, "2.2"));
      supplier.getProducts().add(
          new Product(supplier, "AxeGrinder", "Gaming Mouse", 42.00));

      session.save(supplier);
      tx.commit();
    }

    this.session = SessionUtil.getSession();
    this.tx = this.session.beginTransaction();
  }

  @AfterMethod
  public void closeSession() {
    session.createQuery("delete from Product").executeUpdate();
    session.createQuery("delete from Supplier").executeUpdate();
    if (tx.isActive()) {
      tx.commit();
    }
    if (session.isOpen()) {
      session.close();
    }
  }

}
