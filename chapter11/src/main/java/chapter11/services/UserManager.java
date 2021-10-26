package chapter11.services;

import chapter11.model.User;

public interface UserManager {
  User addUser(String userName);

  User findUserByName(String userName);

  User updateUser(User user);
}
