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

public class GetPostsServlet extends ServletBase {
  @Override
  protected void doGet(
    HttpServletRequest req, HttpServletResponse resp
  ) throws ServletException, IOException {
    List<PostDTO> posts = SessionUtil.returnFromSession(session ->
      getPosts(
        session,
        req.getParameter("userName"),
        req.getParameter("term"))
    );
    write(
      resp,
      HttpServletResponse.SC_OK,
      posts
    );
  }

  private List<PostDTO> getPosts(
    Session session,
    String userName,
    String term
  ) {
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

    Query<Post> postQuery = session
      .createQuery(
        "from Post p order by p.createDate ",
        Post.class
      );

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
