package chapter04.general;

import chapter04.model.SimpleObject;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class SaveOrUpdateTest {
  @Test
  public void testSaveOrUpdateEntity() {
    Long id;
    SimpleObject obj;
    try (Session session=SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      // this only works for simple objects
      session
          .createQuery("delete from SimpleObject")
          .executeUpdate();
      tx.commit();
    }

    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      obj = new SimpleObject();

      obj.setKey("Open Source and Standards");
      obj.setValue(14L);

      session.save(obj);
      assertNotNull(obj.getId());

      id = obj.getId();

      tx.commit();
    }

    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      obj.setValue(12L);

      // if the key didn't exist in the database,
      // it would after this call.
      session.saveOrUpdate(obj);

      tx.commit();
    }

    // saveOrUpdate() will update a row in the database
    // if one matches. This is what one usually expects.
    assertEquals(id, obj.getId());

    try (Session session = SessionUtil.getSession()) {
      List<SimpleObject> objects=session
          .createQuery("from SimpleObject", SimpleObject.class)
          .list();

      assertEquals(objects.size(), 1);
    }
  }
}
