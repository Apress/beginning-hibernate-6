package chapter11.servlets;

import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

import static org.testng.Assert.assertEquals;

public class BadAddUserServletTest extends TestBase{
  String getServletName() {
    return "badadduser";
  }

  @Test
  void emptyUserNameProvided()
    throws IOException, InterruptedException {
    HttpResponse<String> response =
      issueRequest(getServletName()+"?userName=");
    System.out.println(response.body());

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
  }

  @Test
  void noUserNameProvided()
    throws IOException, InterruptedException {
    HttpResponse<String> response =
      issueRequest(getServletName());
    System.out.println(response.body());

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
  }


  @Test
  void runAddUser()
    throws IOException, InterruptedException {
    HttpResponse<String> response =
      issueRequest(getServletName()+"?userName=ts");
    System.out.println(response.body());

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK
    );

    response = issueRequest("badadduser?userName=ts");
    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
  }
}
