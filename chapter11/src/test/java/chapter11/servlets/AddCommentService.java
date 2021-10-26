package chapter11.servlets;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AddCommentService extends BaseService {
  static HttpResponse<String> addComment(
    Integer id,
    String content,
    String userName
  ) throws IOException, InterruptedException {
    String path = String.format(
      "addcomment?id=%s&content=%s&userName=%s",
      id,
      encode(content),
      encode(userName));
    return issueRequest(path);
  }
}
