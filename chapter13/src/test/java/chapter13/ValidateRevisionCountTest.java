package chapter13;

import chapter13.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class ValidateRevisionCountTest extends BaseTest {
  @Test
  public void validateRevisionCount() {
    SessionUtil.doWithSession((session) -> {
      AuditReader reader = AuditReaderFactory.get(session);

      List<Number> revisions =
        reader.getRevisions(User.class, userId[0]);

      assertEquals(revisions.size(), 3);
    });
  }

}

