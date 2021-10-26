package chapter10.first;

import chapter10.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestNoFilter extends TestBase {
  @Test
  public void testSimpleQuery() {
    SessionUtil.doWithSession((session) -> {
      Query<User> query = session.createQuery("from User", User.class);
      List<User> users = query.list();
      assertEquals(users.size(), 4);
    });
  }
}
