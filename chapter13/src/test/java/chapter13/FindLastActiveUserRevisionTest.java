package chapter13;

import chapter13.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FindLastActiveUserRevisionTest extends BaseTest {
  @Test
  public void findLastActiveUserRevision() {
    SessionUtil.doWithSession((session) -> {
      User user = getUserWhenActive(session);
      System.out.println(user);
      assertEquals(user.getDescription(), "1description");
    });
  }

  protected User getUserWhenActive(Session session) {
    AuditReader reader = AuditReaderFactory.get(session);
    AuditQuery query = reader.createQuery()
      .forRevisionsOfEntity(User.class, true, false)
      .addOrder(AuditEntity.revisionNumber().desc())
      .setMaxResults(1)
      .add(AuditEntity.id().eq(userId[0]))
      .add(AuditEntity.property("active").eq(true));

    User user = (User) query.getSingleResult();
    return user;
  }

}
