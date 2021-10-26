package chapter11.servlets;

import java.io.IOException;
import java.net.http.HttpResponse;

public class GetPostService extends BaseService {
  static HttpResponse<String> getPost(Integer id)
    throws IOException, InterruptedException {
    return issueRequest(
      String.format("getpost?id=%d", id)
    );
  }
}
