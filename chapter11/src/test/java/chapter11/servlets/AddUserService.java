package chapter11.servlets;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AddUserService extends BaseService {
  static HttpResponse<String> addUser(
    String userName)
    throws IOException, InterruptedException {
    String path = String.format(
      "adduser?userName=%s",
      encode(userName)
    );
    return issueRequest(path);
  }
}
