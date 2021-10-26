package chapter11.servlets;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AddPostService extends BaseService {
  static HttpResponse<String> addPost(
    String title,
    String content,
    String userName
  ) throws IOException, InterruptedException {
    String path = String.format(
      "addpost?title=%s&content=%s&userName=%s",
      encode(title),
      encode(content),
      encode(userName));
    return issueRequest(path);
  }
}
