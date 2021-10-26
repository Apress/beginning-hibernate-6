package ch12;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class PostEndpoint {
  @Inject
  PostManager postManager;

  @GET
  @Transactional
  public List<Post> getPosts() {
    return postManager.getPosts();
  }

  @POST
  @Transactional
  public Post addPost(Post post) {
    return postManager.savePost(post);
  }
}
