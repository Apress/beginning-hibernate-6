package chapter11.servlets;

import chapter11.dto.PostDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class GetPostsServletTest
  extends TestBase {
  TypeReference<List<PostDTO>> listOfPosts =
    new TypeReference<>() {
    };

  @BeforeMethod
  void createUsersAndPosts() throws IOException, InterruptedException {
    List<Integer> errorCodes = List.of(
      AddUserService.addUser("jbo"),
      AddUserService.addUser("ts"),
      AddPostService.addPost("raccoons 1", "raccoons are cool", "jbo"),
      AddPostService.addPost("i like dogs", "see title", "jbo"),
      AddPostService.addPost("never seen no cat", "what are cats", "jbo"),
      AddPostService.addPost("raccoons 2", "raccoons are trash pandas", "ts"),
      AddPostService.addPost("dogs are good", "i named mine scooby", "ts")
    )
      .stream()
      .map(HttpResponse::statusCode)
      .filter(status -> status != 200)
      .collect(Collectors.toList());
    if (errorCodes.size() > 0) {
      throw new RuntimeException(
        "An error was encountered seeding data"
      );
    }
  }

  @DataProvider
  Object[][] searchCriteria() {
    return new Object[][]{
      {null, null, 5, "all posts"},
      {"jbo", null, 3, "jbo posts"},
      {"jbo", "cat", 1, "jbo cat posts"},
      {null, "raccoons", 2, "raccoon posts"},
      {"arl", null, 0, "invalid user posts"},
      {null, "crow", 0, "search term with no results"},
      {"ts", "cat", 0, "ts has no cat posts"}
    };
  }

  @Test(dataProvider = "searchCriteria")
  void getPosts(String userName, String term, int count, String desc)
    throws IOException, InterruptedException {
    HttpResponse<String> response =
      GetPostsService.getPosts(userName, term);

    assertEquals(
      response.statusCode(),
      HttpServletResponse.SC_OK
    );

    List<PostDTO> dtos = mapper.readValue(
      response.body(),
      listOfPosts);

    System.out.println(dtos);
    assertEquals(dtos.size(), count);
  }
}
