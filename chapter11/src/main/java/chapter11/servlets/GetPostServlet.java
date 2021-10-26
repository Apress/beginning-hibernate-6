package chapter11.servlets;

import chapter11.dto.CommentDTO;
import chapter11.dto.PostDTO;
import chapter11.model.Post;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class GetPostServlet extends ServletBase {
  @Override
  protected void doGet(
    HttpServletRequest req,
    HttpServletResponse resp)
    throws ServletException, IOException {
    try {
      Map<String, String> input = getValidatedParameters(req, "id");
      Integer id = Integer.parseInt(input.get("id"));

      PostDTO postDTO = SessionUtil
        .returnFromSession(session -> getPost(session, id));

      write(
        resp,
        HttpServletResponse.SC_OK,
        postDTO
      );
    } catch (Exception e) {
      handleException(resp, e);
    }
  }

  protected void handleException(
    HttpServletResponse resp,
    Exception e
  ) throws IOException {
    if (e.getCause() instanceof ObjectNotFoundException) {
      write(
        resp,
        HttpServletResponse.SC_NOT_FOUND,
        Map.of("error", e.getCause().getMessage())
      );
    } else {
      writeError(resp, e);
    }

  }

  protected PostDTO getPost(Session session, Integer id) {
    Post post = session.load(Post.class, id);
    PostDTO postDTO = new PostDTO();

    postDTO.setId(id);
    postDTO.setTitle(post.getTitle());
    postDTO.setContent(post.getContent());
    postDTO.setCreatedDate(post.getCreateDate());
    postDTO.setUser(post.getUser().getName());

    postDTO.setComments(
      post
        .getComments()
        .stream()
        .map(
          comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedDate(comment.getCreateDate());
            commentDTO.setUser(comment.getUser().getName());
            return commentDTO;
          })
        .collect(Collectors.toList())
    );
    return postDTO;
  }
}
