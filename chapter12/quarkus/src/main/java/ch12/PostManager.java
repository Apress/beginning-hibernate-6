package ch12;

import java.util.List;

public interface PostManager {
  Post savePost(Post post);

  List<Post> getPosts();
}
