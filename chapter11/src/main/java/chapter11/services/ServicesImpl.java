package chapter11.services;

import lombok.experimental.Delegate;

public class ServicesImpl implements Services {
  @Delegate
  UserManager userManager = new UserManagerImpl();

  @Delegate
  PostManager postmanager = new PostManagerImpl();

  @Delegate
  CommentManager commentManager = new CommentManagerImpl();
}
