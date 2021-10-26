package chapter13;

import chapter13.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class ValidateRevisionSnapshotTest extends BaseTest {
  @Test
  public void testUserData() {
    SessionUtil.doWithSession((session) -> {
      AuditReader reader = AuditReaderFactory.get(session);

      List<Integer> revisions =
        reader.getRevisions(User.class, userId[0])
          .stream()
          .map(Number::intValue)
          .collect(Collectors.toList());

      int indexOfLastRevision = revisions.size() - 1;
      int lastRevision = revisions.get(indexOfLastRevision);
      User lastUser = findUserAtRevision(reader, lastRevision);
      User prevUser = findUserAtRevision(reader, lastRevision - 1);

      assertTrue(lastRevision - 1 > revisions.get(indexOfLastRevision - 1));
      assertNotEquals(lastUser.isActive(), prevUser.isActive());
    });
  }
}
