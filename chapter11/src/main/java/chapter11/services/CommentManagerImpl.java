package chapter11.services;

import chapter11.model.Comment;
import chapter11.model.Post;
import chapter11.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class CommentManagerImpl implements CommentManager {
  UserManagerImpl userManager = new UserManagerImpl();

  @Override
  public Comment addComment(Post post, String content, String userName) {
    return SessionUtil.returnFromSession(session -> addComment(session, post, content, userName));
  }

  @Override
  public List<Comment> findCommentsByPost(Post post) {
    return SessionUtil.returnFromSession(session -> findCommentsByPost(session, post));
  }

  private List<Comment> findCommentsByPost(Session session, Post post) {
    Query<Comment> commentQuery = session.createQuery("from Comment where post=:post", Comment.class);
    commentQuery.setParameter("post", post);
    return commentQuery.list();
  }

  private Comment addComment(Session session, Post post, String content, String userName) {
    User user = userManager.findUserByName(session, userName);
    session.merge(post);
    Comment comment = new Comment();
    comment.setContent(content);
    comment.setUser(user);
    comment.setPost(post);
    comment.setCreateDate(LocalDateTime.now());
    session.save(comment);
    post.getComments().add(comment);
    return comment;
  }
}
