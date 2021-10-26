package ch12;

import io.activej.http.AsyncServlet;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.launcher.Launcher;
import io.activej.launchers.http.HttpServerLauncher;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import static io.activej.http.HttpMethod.GET;

public class PostApp
  extends HttpServerLauncher {
  @Provides
  ObjectMapperFactory mapper() {
    return new ObjectMapperFactory();
  }

  @Provides
  SessionFactory sessionFactory() {
    StandardServiceRegistry registry =
      new StandardServiceRegistryBuilder()
        .configure()
        .build();
    SessionFactory factory = new MetadataSources(registry)
      .buildMetadata()
      .buildSessionFactory();
    return factory;
  }

  @Provides
  PostManager getPostManager(SessionFactory factory) {
    return new HibernatePostManager(factory);
  }

  @Provides
  Endpoints endpoints(
    PostManager manager,
    ObjectMapperFactory mapperFactory
  ) {
    return new Endpoints(manager, mapperFactory);
  }

  @Provides
  AsyncServlet servlet(
    Endpoints endpoints
  ) {
    return RoutingServlet.create()
      .map(GET, "/", endpoints::getPosts)
      .map(GET, "/add", endpoints::addPost);
  }

  public static void main(String[] args) throws Exception {
    Launcher launcher = new PostApp();
    launcher.launch(args);
  }
}
