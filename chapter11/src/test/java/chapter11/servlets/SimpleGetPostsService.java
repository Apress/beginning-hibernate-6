package chapter11.servlets;

import java.io.IOException;
import java.net.http.HttpResponse;

public class SimpleGetPostsService extends BaseService{
  static HttpResponse<String> getSimplePosts()
    throws IOException, InterruptedException {
    return issueRequest("simplegetposts");
  }
}
