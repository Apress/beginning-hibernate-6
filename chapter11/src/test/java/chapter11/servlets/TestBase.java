package chapter11.servlets;

import chapter11.model.Comment;
import chapter11.model.Post;
import chapter11.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.hibernate.query.Query;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class TestBase {
  Undertow server;
  TypeReference<Map<String, Object>> mapOfMaps =
    new TypeReference<>() {
    };
  protected ObjectMapper mapper = new ObjectMapper()
    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  {
    mapper.registerModule(new JavaTimeModule());
  }

  @BeforeClass
  void start() throws ServletException, IOException {
    DeploymentInfo servletBuilder = Servlets.deployment()
      .setClassLoader(TestBase.class.getClassLoader())
      .setContextPath("/myapp")
      .setDeploymentName("test.war");
    populateServlets(servletBuilder);

    DeploymentManager manager = Servlets
      .defaultContainer()
      .addDeployment(servletBuilder);
    manager.deploy();
    PathHandler path = Handlers.path(Handlers.redirect("/myapp"))
      .addPrefixPath("/myapp", manager.start());
    server = Undertow.builder()
      .addHttpListener(8080, "localhost")
      .setHandler(path)
      .build();
    server.start();
  }

  private void populateServlets(DeploymentInfo servletBuilder)
    throws IOException {
    Map<String, Object> servlets = mapper
      .readValue(
        this
          .getClass()
          .getResourceAsStream("/servlets.json"
          ), mapOfMaps);
    servlets.entrySet().forEach(entry -> {
      Map<String, Object> data =
        (Map<String, Object>) entry.getValue();
      try {
        var servlet = Servlets.servlet(
          entry.getKey(),
          (Class<? extends Servlet>) Class.forName(
            data.get("class").toString()
          ));
        if (data.containsKey("initParams")) {
          Map<String, Object> params =
            (Map<String, Object>) data.get("initParams");
          params.entrySet().forEach(param -> {
            servlet.addInitParam(
              param.getKey(),
              param.getValue().toString()
            );
          });
        }
        servlet.addMapping(data.get("mapping").toString());
        servletBuilder.addServlets(servlet);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
  }

  @AfterClass
  void stop() {
    server.stop();
  }

  @BeforeMethod
  void clearAll() {
    SessionUtil.doWithSession(session -> {
      Query<Comment> commentQuery =
        session.createQuery("from Comment", Comment.class);
      for (var obj : commentQuery.list()) {
        session.delete(obj);
      }

      Query<Post> postQuery =
        session.createQuery("from Post", Post.class);
      for (Post post : postQuery.list()) {
        session.delete(post);
      }
      Query<User> query =
        session.createQuery("from User", User.class);
      for (User user : query.list()) {
        session.delete(user);
      }
    });
  }

  protected HttpResponse<String> issueRequest(String path)
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
