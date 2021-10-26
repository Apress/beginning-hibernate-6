package chapter11.services;

import chapter11.model.Comment;
import chapter11.model.Post;
import chapter11.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostManagerImpl implements PostManager {
  UserManagerImpl userManager = new UserManagerImpl();

  @Override
  public Post addPost(String title, String content, String userName) {
    return SessionUtil.returnFromSession(session -> addPost(session, title, content, userName));
  }

  public Post addPost(Session session, String title, String content, String userName) {
    Post post;
    try {
      post = findPost(session, title, userName);
    } catch (NoResultException nre) {
      try {
        User user = userManager.findUserByName(session, userName);
        post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        session.save(post);
      } catch (NoResultException noUser) {
        throw new NoResultException("user " + userName + " not found");
      }
    }
    return post;
  }

  private Post findPost(Session session, String title, String userName) {
    Query<Post> query = session.createQuery(
      "from Post p where p.user.name=:name and p.title = :title",
      Post.class
    );
    query.setParameter("name", userName);
    query.setParameter("title", title);
    return query.getSingleResult();
  }

  @Override
  public List<Post> findPosts() {
    return SessionUtil.returnFromSession(this::findPosts);
  }

  private List<Post> findPosts(Session session) {
    Query<Post> query = session
      .createQuery(
        "from Post p order by p.createDate ",
        Post.class
      );
    query.setMaxResults(20);
    return query
      .stream()
      .map(e -> {
        e.getUser();
        return e;
      })
      .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public List<Post> findPostsByCriteria(String userName, String term) {
    return SessionUtil.returnFromSession(session -> {

      if (userName != null && !userName.isEmpty()) {
        session
          .enableFilter("byName")
          .setParameter("name", userName);
      }

      if (term != null && !term.isEmpty()) {
        session
          .enableFilter("byTerm")
          .setParameter("term", "%" + term + "%");
      }
      return findPosts(session);
    });
  }
}
