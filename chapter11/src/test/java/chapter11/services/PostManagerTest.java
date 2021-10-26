package chapter11.services;

import chapter11.model.Post;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class PostManagerTest extends TestBase {
  @BeforeMethod
  void addUsers() {
    services.addUser("jbo");
    services.addUser("ts");
  }

  @Test
  public void testPost() {
    services.addPost("post 1", "this is the content of post 1", "ts");
    List<Post> posts = services.findPosts();
    services.addPost("post 1", "this is the content of post 1", "ts");
    assertEquals(services.findPosts().size(), 1);
  }

  @Test(expectedExceptions = RuntimeException.class)
  public void testBadPost() {
    // note bad user
    services.addPost("post 2", "this is the content of post 2", "al");
  }

  @Test
  public void testPostFilter() {
    services.addPost("giraffes are cool", "this is a giraffe", "ts");
    services.addPost("mecha-raccoons forever", "this is a raccoon", "jbo");
    assertEquals(services.findPostsByCriteria("ts", null).size(), 1);
    assertEquals(services.findPostsByCriteria(null, "gir").size(), 1);
    assertEquals(services.findPostsByCriteria("ts", "gir").size(), 1);
    assertEquals(services.findPosts().size(), 2);
  }

  @Test
  public void testAddComment() {
    Post post = services.addPost("giraffes are cool", "this is a giraffe", "ts");
    services.addComment(post, "raccoons are cooler", "jbo");
    List<Post> posts = services.findPostsByCriteria("ts", null);
    posts.forEach(p -> {
      System.out.println(p);
      services.findCommentsByPost(p).forEach(System.out::println);
    });
  }
}
