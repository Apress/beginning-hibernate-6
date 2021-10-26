package ch12;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class HibernatePostManager implements PostManager {
  private final SessionFactory sessionFactory;

  public HibernatePostManager(SessionFactory factory) {
    this.sessionFactory = factory;
  }

  @Override
  public List<Post> getPosts() {
    return returnFromSession(session -> {
      Query<Post> postQuery = session.createQuery(
        "from Post p order by p.createdAt desc",
        Post.class
      );
      postQuery.setMaxResults(20);
      return postQuery.list();
    });
  }

  @Override
  public Post savePost(String title, String content) {
    return returnFromSession(session -> {
      Post post = new Post();
      post.setTitle(title);
      post.setContent(content);
      post.setCreatedAt(new Date());
      session.save(post);
      return post;
    });
  }

  public <T> T returnFromSession(Function<Session, T> command) {
    try (Session session = sessionFactory.openSession()) {
      Transaction tx = null;
      try {
        tx = session.beginTransaction();

        return command.apply(session);
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        if (tx != null) {
          if (tx.isActive() &&
            !tx.getRollbackOnly()) {
            tx.commit();
          } else {
            tx.rollback();
          }
        }
      }
    }
  }
}
