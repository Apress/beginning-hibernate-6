package chapter13;

import chapter13.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class HandleDeletedRevisionsTest extends BaseTest {
  @BeforeClass
  void deleteUser() {
    SessionUtil.doWithSession(session -> {
      User user = session.load(User.class, userId[0]);
      session.delete(user);
    });
  }

  @Test
  public void countRevisions() {
    SessionUtil.doWithSession(session -> {
      AuditReader reader = AuditReaderFactory.get(session);

      List<Number> revisions =
        reader.getRevisions(User.class, userId[0]);

      assertEquals(revisions.size(), 4);
    });
  }

  @Test
  public void findRevisionNoDeleted() {
    User user = runQueryForVersion(false);
    assertNull(user);
  }

  @Test
  public void findRevisionDeleted() {
    User user = runQueryForVersion(true);
    assertNotNull(user);
    assertNull(user.getName());
    assertNull(user.getDescription());
  }

  private User runQueryForVersion(
    boolean includeDeleted
  ) {
    return SessionUtil.returnFromSession(session -> {
      AuditReader reader = AuditReaderFactory.get(session);

      User user = reader.find(
        User.class,
        "chapter13.model.User",
        userId[0],
        6,
        includeDeleted
      );
      return user;
    });
  }
}
