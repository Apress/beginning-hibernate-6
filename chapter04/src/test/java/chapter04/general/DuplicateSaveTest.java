package chapter04.general;

import chapter04.model.SimpleObject;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class DuplicateSaveTest {
  @Test
  public void duplicateSaveTest() {
    Long id;
    SimpleObject obj;

    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      obj = new SimpleObject();

      obj.setKey("Open Source and Standards");
      obj.setValue(10L);

      session.save(obj);
      assertNotNull(obj.getId());

      id = obj.getId();

      tx.commit();
    }

    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      obj.setValue(12L);

      // this is not good behavior!
      session.save(obj);

      tx.commit();
    }

    // note that save() creates a new row in the database!
    // this is wrong behavior. Don't do this!
    assertNotEquals(id, obj.getId());

    try (Session session = SessionUtil.getSession()) {
      List<SimpleObject> objects=session
          .createQuery("from SimpleObject", SimpleObject.class)
          .list();

      // again, this is a value we DO NOT WANT.
      assertEquals(objects.size(), 2);
    }
  }
}
