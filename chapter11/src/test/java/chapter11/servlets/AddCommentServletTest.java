package chapter11.servlets;

import chapter11.dto.PostDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

import static org.testng.Assert.assertEquals;

public class AddCommentServletTest extends TestBase {
  PostDTO post = null;

  @BeforeMethod
  void createUsersAndPosts()
    throws IOException, InterruptedException {
    AddUserService.addUser("jbo");
    AddUserService.addUser("ts");

    HttpResponse<String> postData =
      AddPostService.addPost("raccoons", "raccoons are neat", "jbo");

    // this is how we get the post's id.
    post = mapper.readValue(postData.body(), PostDTO.class);
  }


  @Test
  void testAddComment() throws IOException, InterruptedException {
    HttpResponse<String> response =
      GetPostService.getPost(post.getId());
    validatePost(response, 0);

    response = AddCommentService.addComment(
      post.getId(),
      "what's the deal with raccoons, really",
      "ts"
    );
    assertEquals(response.statusCode(), 200);
    validatePost(response, 1);

    response = AddCommentService.addComment(
      post.getId(),
      "they're the coolest",
      "jbo"
    );
    assertEquals(response.statusCode(), 200);
    validatePost(response, 2);


    response =
      GetPostService.getPost(post.getId());
    validatePost(response, 2);
  }

  @Test
  void testInvalidGetPost()
    throws IOException, InterruptedException {

    HttpResponse<String> response =
      GetPostService.getPost(post.getId() + 1);

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_NOT_FOUND
    );
  }

  void validatePost(
    HttpResponse<String> response,
    int commentSize
  ) throws IOException {
    assertEquals(response.statusCode(), 200);

    PostDTO retrieved = mapper.readValue(response.body(), PostDTO.class);

    assertEquals(retrieved.getComments().size(), commentSize);
    assertEquals(retrieved.getTitle(), "raccoons");
  }
}
