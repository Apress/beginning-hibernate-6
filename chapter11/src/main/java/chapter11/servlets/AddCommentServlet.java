package chapter11.servlets;

import chapter11.dto.CommentDTO;
import chapter11.dto.PostDTO;
import chapter11.model.Comment;
import chapter11.model.Post;
import chapter11.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class AddCommentServlet extends GetPostServlet {
  @Override
  protected void doGet(
    HttpServletRequest req,
    HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      Map<String, String> input = getValidatedParameters(
        req,
        "id",
        "userName",
        "content"
      );
      Integer id = Integer.parseInt(input.get("id"));

      PostDTO postDTO = SessionUtil
        .returnFromSession(session ->
          addComment(
            session,
            id,
            input.get("userName"),
            input.get("content")
          )
        );

      write
        (resp,
          HttpServletResponse.SC_OK,
          postDTO
        );
    } catch (Exception e) {
      handleException(resp, e);
    }
  }

  PostDTO addComment(
    Session session,
    Integer id,
    String userName,
    String content
  ) {
    Query<User> userQuery = session.createQuery(
      "from User u where u.name=:name",
      User.class
    );
    userQuery.setParameter("name", userName);
    User user = userQuery.getSingleResult();

    Post post = session.load(Post.class, id);

    Comment comment = new Comment();
    comment.setUser(user);
    comment.setPost(post);
    comment.setContent(content);
    comment.setCreateDate(LocalDateTime.now());

    session.save(comment);

    post.getComments().add(comment);

    return getPost(session, id);
  }
}
