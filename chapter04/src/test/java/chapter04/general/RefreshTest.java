package chapter04.general;

import chapter04.model.SimpleObject;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

public class RefreshTest {
  @Test
  public void testRefresh() {
    Long id;
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      SimpleObject simpleObject = new SimpleObject();

      simpleObject.setKey("testMerge");
      simpleObject.setValue(1L);

      session.save(simpleObject);

      id = simpleObject.getId();

      tx.commit();
    }

    SimpleObject so = ValidateSimpleObject.validate(id, 1L, "testMerge");

    // the 'so' object is detached here
    so.setValue(2L);

    try (Session session = SessionUtil.getSession()) {
      // note that refresh is a read,
      // so no TX is necessary unless an update occurs later
      session.refresh(so);
    }

    ValidateSimpleObject.validate(id, 1L, "testMerge");
  }
}
