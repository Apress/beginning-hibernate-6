package chapter13;

import chapter13.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class RevertDataTest extends
  FindLastActiveUserRevisionTest {

  @Test
  public void revertUserData() {
    SessionUtil.doWithSession((session) -> {
      User auditUser = getUserWhenActive(session);

      assertEquals(auditUser.getDescription(), "1description");

      // now we copy the audit data into the "current user."
      User user = session.load(User.class, userId[0]);

      assertFalse(user.isActive());
      user.setActive(auditUser.isActive());
      user.setDescription(auditUser.getDescription());
      user.setGroups(auditUser.getGroups());
    });

    // let's make sure the "current user" looks like what we expect
    SessionUtil.doWithSession((session) -> {
      User user = session.load(User.class, userId[0]);
      assertTrue(user.isActive());
      assertEquals(user.getDescription(), "1description");
    });
  }
}
