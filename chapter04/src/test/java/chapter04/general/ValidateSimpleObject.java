package chapter04.general;

import chapter04.model.SimpleObject;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;

import static org.testng.Assert.assertEquals;

public class ValidateSimpleObject {
  public static SimpleObject validate(
      Long id,
      Long expectedValue,
      String expectedKey) {
    SimpleObject so = null;
    try (Session session = SessionUtil.getSession()) {
      // will throw an Exception if the id isn't found
      // in the database
      so = session.load(SimpleObject.class, id);

      assertEquals(so.getKey(), expectedKey);
      assertEquals(so.getValue(), expectedValue);
    }

    return so;
  }
}
