package chapter11.services;

import chapter11.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

public class UserManagerImpl implements UserManager {
  @Override
  public User addUser(String userName) {
    return SessionUtil.returnFromSession(session -> addUser(session, userName));
  }

  public User addUser(Session session, String userName) {
    User u;
    try {
      u = findUserByName(session, userName);
    } catch (NoResultException nre) {
      u = new User(userName, true);
      session.save(u);
    }
    return u;
  }

  @Override
  public User findUserByName(String userName) {
    return SessionUtil.returnFromSession(session -> findUserByName(session, userName));
  }

  public User findUserByName(Session session, String userName) {
    Query<User> query = session
      .createQuery("from User u where u.name = :name", User.class);
    query.setParameter("name", userName);
    return query.getSingleResult();
  }

  @Override
  public User updateUser(User user) {
    return SessionUtil.returnFromSession(session -> updateUser(session, user));
  }

  public User updateUser(Session session, User user) {
    session.saveOrUpdate(user);
    return user;
  }
}
