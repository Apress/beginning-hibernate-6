package chapter11.services;

import chapter11.model.Comment;
import chapter11.model.Post;

import java.util.List;

public interface CommentManager {
  Comment addComment(Post post, String content, String userName);

  List<Comment> findCommentsByPost(Post post);
}
