package chapter11.servlets;

import chapter11.dto.PostDTO;
import chapter11.model.Post;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleGetPostsServlet extends ServletBase {
  @Override
  protected void doGet(
    HttpServletRequest req, HttpServletResponse resp
  ) throws ServletException, IOException {
    List<PostDTO> posts = SessionUtil.returnFromSession(session ->
      getPosts(session));
    write(
      resp,
      HttpServletResponse.SC_OK,
      posts
    );
  }

  private List<PostDTO> getPosts(Session session) {
    Query<Post> postQuery = session
      .createQuery("from Post p", Post.class);
    postQuery.setMaxResults(20);
    return postQuery.list().stream().map(post -> {
      PostDTO dto = new PostDTO();
      dto.setId(post.getId());
      dto.setUser(post.getUser().getName());
      dto.setContent(post.getContent());
      dto.setTitle(post.getTitle());
      dto.setCreatedDate(post.getCreateDate());
      return dto;
    }).collect(Collectors.toList());
  }
}
