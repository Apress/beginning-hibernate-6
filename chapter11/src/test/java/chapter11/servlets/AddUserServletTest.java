package chapter11.servlets;

import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class AddUserServletTest
  extends BadAddUserServletTest {
  String getServletName() {
    return "adduser";
  }

  @Override
  @Test
  void runAddUser()
    throws IOException, InterruptedException {
    HttpResponse<String> response =
      issueRequest("adduser?userName=jbo");

    Map<String, Object> data =
      mapper.readValue(response.body(), mapOfMaps);

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK
    );

    response = SimpleGetPostsService.getSimplePosts();

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK
    );
  }
}
