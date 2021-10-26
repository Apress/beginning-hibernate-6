package chapter11.servlets;

import chapter11.dto.UserDTO;
import chapter11.model.User;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class BadAddUserServlet extends ServletBase {
  @Override
  protected void doGet(
    HttpServletRequest req,
    HttpServletResponse resp
  )
    throws ServletException, IOException {
    try {
      Map<String, String> input =
        getValidatedParameters(req, "userName");

      User user = SessionUtil.returnFromSession(
        session -> createUser(session, input.get("userName"))
      );
      write(resp,
        HttpServletResponse.SC_OK,
        user);
    } catch (Exception e) {
      writeError(resp, e);
    }
  }

  private User createUser(Session session, String userName) {
    User entity;
    try {
      Query<User> query = session.createQuery(
        "from User u where u.name=:name",
        User.class);
      query.setParameter("name", userName);

      entity = query.getSingleResult();
    } catch (NoResultException nre) {
      entity = new User(userName, true);
      session.save(entity);
    }

    return entity;
  }
}
