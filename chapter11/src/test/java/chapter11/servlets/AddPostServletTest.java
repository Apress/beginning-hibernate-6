package chapter11.servlets;

import chapter11.dto.PostDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class AddPostServletTest
  extends TestBase {

  TypeReference<List<PostDTO>> listOfPosts =
    new TypeReference<>() {
    };

  void addPost()
    throws IOException, InterruptedException {

    HttpResponse<String> response = AddPostService.addPost(
      "test post",
      "my test post",
      "jbo"
    );
    System.out.println(response.body());

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK,
      "invalid user"
    );

    PostDTO data =
      mapper.readValue(
        response.body(),
        PostDTO.class
      );

    response = SimpleGetPostsService.getSimplePosts();

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK
    );

    System.out.println(response.body());

    List<PostDTO> dtos=mapper.readValue(response.body(),
      listOfPosts);
    System.out.println(dtos);
    assertEquals(dtos.size(), 1);
  }


  @Test(
    expectedExceptions = AssertionError.class,
    expectedExceptionsMessageRegExp = "invalid user.*"
  )
  void addPostNoUser() throws IOException, InterruptedException {
    addPost();
  }

  @Test
  void addPostWithValidUser()
    throws IOException, InterruptedException {

    HttpResponse<String> response =
      AddUserService.addUser("jbo");

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK
    );

    addPost();
  }
}
