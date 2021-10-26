package chapter11.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class HelloWorld extends ServletBase {
  @Override
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException {
    Map<String, String> data=Map.of(
      "response", this.getInitParameter("message")
    );
    write(response, HttpServletResponse.SC_OK, data);
  }
}
