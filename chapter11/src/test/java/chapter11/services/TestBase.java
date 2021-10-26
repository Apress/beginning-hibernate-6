package chapter11.services;

import chapter11.model.Comment;
import chapter11.model.Post;
import chapter11.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.query.Query;
import org.testng.annotations.BeforeMethod;

public class TestBase {
  Services services = new ServicesImpl();

  @BeforeMethod
  void clearAll() {
    SessionUtil.doWithSession(session -> {
      Query<Comment> commentQuery = session.createQuery("from Comment", Comment.class);
      for (var obj : commentQuery.list()) {
        session.delete(obj);
      }

      Query<Post> postQuery = session.createQuery("from Post", Post.class);
      for (Post post : postQuery.list()) {
        session.delete(post);
      }
      Query<User> query = session.createQuery("from User", User.class);
      for (User user : query.list()) {
        session.delete(user);
      }
    });
  }
}
