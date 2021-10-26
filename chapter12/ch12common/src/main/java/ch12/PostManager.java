package ch12;

import java.util.List;

public interface PostManager {
  List<Post> getPosts();

  Post savePost(String title, String content);
}
