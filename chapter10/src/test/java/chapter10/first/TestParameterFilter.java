package chapter10.first;

import chapter10.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestParameterFilter extends TestBase {
  @DataProvider
  Object[][] statuses() {
    return new Object[][]{
      {true, 3},
      {false, 1}
    };
  }

  @Test(dataProvider = "statuses")
  public void testFilter(boolean status, int count) {
    SessionUtil.doWithSession((session) -> {
      Query<User> query = session.createQuery("from User", User.class);

      session
        .enableFilter("byStatus")
        .setParameter("status", status);

      List<User> users = query.list();
      assertEquals(users.size(), count);
    });
  }
}
