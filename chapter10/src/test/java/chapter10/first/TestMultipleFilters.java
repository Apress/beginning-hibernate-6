package chapter10.first;

import chapter10.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestMultipleFilters extends TestBase {
  @Test
  public void testGroupFilter() {
    SessionUtil.doWithSession((session) -> {
      Query<User> query = session.createQuery("from User", User.class);

      session
        .enableFilter("byGroup")
        .setParameter("group", "group4");

      List<User> users = query.list();
      assertEquals(users.size(), 2);

      session
        .enableFilter("byGroup")
        .setParameter("group", "group1");

      users = (List<User>) query.list();
      assertEquals(users.size(), 1);

      // should be user 1
      assertEquals(users.get(0).getName(), "user1");
    });
  }

  @Test
  public void testBothFilters() {
    SessionUtil.doWithSession((session) -> {
      Query<User> query = session.createQuery("from User", User.class);

      session
        .enableFilter("byGroup")
        .setParameter("group", "group4");
      session
        .enableFilter("byStatus")
        .setParameter("status", Boolean.TRUE);

      List<User> users = query.list();

      assertEquals(users.size(), 1);
      assertEquals(users.get(0).getName(), "user4");
    });
  }

}

