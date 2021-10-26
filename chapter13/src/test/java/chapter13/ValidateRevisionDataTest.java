package chapter13;

import chapter13.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class ValidateRevisionDataTest extends BaseTest {
  @Test
  public void testUserData() {
    SessionUtil.doWithSession((session) -> {
      AuditReader reader = AuditReaderFactory.get(session);
      List<Integer> revisions =
        reader.getRevisions(User.class, userId[0])
          .stream()
          .map(Number::intValue)
          .collect(Collectors.toList());

      List<User> userRevs =
        revisions
          .stream()
          .map(rev -> findUserAtRevision(reader, rev))
          .collect(Collectors.toList());

      // first revision
      assertEquals(
        userRevs.get(0).getDescription(),
        "description"
      );
      assertEquals(
        userRevs.get(0).getGroups(),
        Set.of("group1")
      );

      // second revision
      assertEquals(
        userRevs.get(1).getDescription(),
        "1description");
      assertEquals(
        userRevs.get(1).getGroups(),
        Set.of("group1", "group2")
      );

      // third, and last, revision
      assertFalse(
        userRevs.get(2).isActive()
      );

      assertEquals(
        session.load(User.class, userId[0]),
        userRevs.get(2)
      );

      System.out.println(reader.getRevisionDate(2));
      System.out.println(reader.getRevisionDate(1));
    });
  }
}
