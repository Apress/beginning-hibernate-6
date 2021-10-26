package ch12;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;

import java.util.List;

public class Endpoints {
  PostManager postManager;
  ObjectMapperFactory mapperFactory;

  public Endpoints(
    PostManager postManager,
    ObjectMapperFactory mapperFactory
  ) {
    this.postManager = postManager;
    this.mapperFactory = mapperFactory;
  }

  HttpResponse getPosts(HttpRequest request) {
    try {
      List<Post> posts = postManager.getPosts();

      return HttpResponse
        .ok200()
        .withJson(mapperFactory
          .buildMapper()
          .writeValueAsString(posts)
        );
    } catch (JsonProcessingException e) {
      return HttpResponse
        .ofCode(500)
        .withPlainText(e.getMessage());
    }
  }

  HttpResponse addPost(HttpRequest request) {
    String title = request.getQueryParameter("title");
    String content = request.getQueryParameter("content");

    try {
      Post post = postManager.savePost(title, content);
      return io.activej.http.HttpResponse
        .ok200()
        .withJson(mapperFactory
          .buildMapper()
          .writeValueAsString(post)
        );
    } catch (JsonProcessingException e) {
      return io.activej.http.HttpResponse
        .ofCode(500)
        .withPlainText(e.getMessage());
    }
  }
}
