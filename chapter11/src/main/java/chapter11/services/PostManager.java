package chapter11.services;

import chapter11.model.Comment;
import chapter11.model.Post;

import java.util.List;

public interface PostManager {
  Post addPost(String title, String content, String userName);

  List<Post> findPosts();

  List<Post> findPostsByCriteria(String user, String term);

}
