package chapter11.servlets;

import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class HelloWorldTest extends TestBase {
  @Test
  public void testHelloWorld()
    throws IOException, InterruptedException {
    HttpResponse<String> response =
      issueRequest("hello");

    Map<String, Object> data =
      mapper.readValue(response.body(), mapOfMaps);

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK
    );
    assertEquals(
      data.get("response"),
      "Hello World"
    );
  }
}
