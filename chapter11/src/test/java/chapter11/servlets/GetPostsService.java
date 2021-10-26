package chapter11.servlets;

import java.io.IOException;
import java.net.http.HttpResponse;

public class GetPostsService extends BaseService {
  static HttpResponse<String> getPosts(String userName, String term)
    throws IOException, InterruptedException {
    StringBuilder path = new StringBuilder("getposts");
    String separator = "?";
    if (userName != null && !userName.isEmpty()) {
      path
        .append(separator)
        .append("userName=")
        .append(userName);
      separator = "&";
    }
    if (term != null && !term.isEmpty()) {
      path
        .append(separator)
        .append("term=")
        .append(term);
    }
    return issueRequest(path.toString());
  }
}
