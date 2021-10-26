package chapter11.servlets;

import chapter11.dto.PostDTO;
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

public class AddPostServlet extends ServletBase {
  @Override
  protected void doGet(
    HttpServletRequest req,
    HttpServletResponse resp
  ) throws ServletException, IOException {
    try {
      Map<String, String> input =
        getValidatedParameters(req, "userName", "title", "content");

      PostDTO postDTO = SessionUtil.returnFromSession(session ->
        createPost(
          session,
          input.get("userName"),
          input.get("title"),
          input.get("content"))
      );
      write(resp, HttpServletResponse.SC_OK, postDTO);
    } catch (Throwable t) {
      writeError(resp, t);
    }
  }

  private PostDTO createPost(
    Session session,
    String userName,
    String title,
    String content) {

    Query<User> userQuery = session.createQuery(
      "from User u where u.name=:name",
      User.class
    );
    userQuery.setParameter("name", userName);

    User user = userQuery.getSingleResult();

    Post post = new Post();
    post.setTitle(title);
    post.setContent(content);
    post.setUser(user);
    post.setCreateDate(LocalDateTime.now());
    session.save(post);

    PostDTO dto = new PostDTO();
    dto.setId(post.getId());
    dto.setContent(post.getContent());
    dto.setTitle(post.getTitle());
    dto.setUser(userName);

    return dto;
  }
}
