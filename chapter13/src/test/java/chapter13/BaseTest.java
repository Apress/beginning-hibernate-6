package chapter13;

import chapter13.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.query.Query;
import org.testng.annotations.BeforeClass;

import javax.persistence.EntityManagerFactory;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

abstract class BaseTest {
  int[] userId = {0, 1};

  User createUser(Session session, String username) {
    User user = new User(username, true);
    user.setDescription("description");
    user.addGroups("group1");
    session.save(user);
    return user;
  }

  @BeforeClass
  public void setup() {
    SessionUtil.forceReload();

    SessionUtil.doWithSession(session -> {
      Query<User> deleteQuery = session.
        createQuery("delete from User u");
      deleteQuery.executeUpdate();
    });

    SessionUtil.doWithSession((session) -> {
      User user = createUser(session, "user1");
      userId[0] = user.getId();
    });

    SessionUtil.doWithSession(session -> {
      User user = createUser(session, "user2");
      userId[1] = user.getId();
    });

    SessionUtil.doWithSession((session) -> {
      User user = session.byId(User.class).load(userId[0]);
      assertTrue(user.isActive());
      assertEquals(user.getDescription(),
        "description");
    });

    SessionUtil.doWithSession((session) -> {
      User user = session.byId(User.class).load(userId[0]);
      user.addGroups("group2");
      user.setDescription("1description");
    });

    SessionUtil.doWithSession((session) -> {
      User user = session.byId(User.class).load(userId[1]);
      user.addGroups("group2");
      user.setDescription("2description");
    });

    SessionUtil.doWithSession((session) -> {
      User user = session.byId(User.class).load(userId[0]);
      user.setActive(false);
    });

    SessionUtil.doWithSession((session) -> {
      User user = session.byId(User.class).load(userId[0]);
      assertFalse(user.isActive());
      assertEquals(user.getDescription(), "1description");
    });
  }

  User findUserAtRevision(
    AuditReader reader,
    Number revision) {
    return findUserAtRevision(
      reader,
      userId[0],
      revision
    );
  }

  User findUserAtRevision(
    AuditReader reader,
    int pk,
    Number revision) {
    reader.find(User.class, pk, revision);
    return reader.find(
      User.class,
      "chapter13.model.User",
      pk,
      revision
    );
  }
}
