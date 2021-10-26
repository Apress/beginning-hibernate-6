package chapter11.servlets;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;

public class BaseService {
  static String encode(String value) {
    return URLEncoder.encode(
      value,
      Charset.defaultCharset()
    );
  }

  static HttpResponse<String> issueRequest(String path)
    throws IOException, InterruptedException {
    HttpClient client = HttpClient.newBuilder().build();

    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("http://localhost:8080/myapp/" + path))
      .timeout(Duration.ofSeconds(3))
      .build();

    HttpResponse<String> response =
      client.send(request, HttpResponse.BodyHandlers.ofString());
    return response;
  }
}
