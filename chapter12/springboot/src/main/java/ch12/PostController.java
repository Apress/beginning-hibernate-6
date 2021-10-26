package ch12;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PostController {
  private final PostRepository postRepository;

  PostController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @GetMapping(value = "/", produces = {"application/json"})
  public List<Post> index() {
    return postRepository.findAll(
      Sort.by(Sort.Direction.DESC,"createdAt")
    );
  }

  @GetMapping(value = "/add", produces = {"application/json"})
  public Post addPost(
    @RequestParam("title") String title,
    @RequestParam("content") String content) {
    Post post = new Post();
    post.setTitle(title);
    post.setContent(content);
    post.setCreatedAt(new Date());
    postRepository.save(post);
    return post;
  }
}
