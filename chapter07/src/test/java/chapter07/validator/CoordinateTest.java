package chapter07.validator;

import chapter07.validated.Coordinate;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;

public class CoordinateTest {
  private void persist(Coordinate entity) {
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      session.persist(entity);
      tx.commit();
    }
  }

  @DataProvider(name = "validCoordinates")
  private Object[][] validCoordinates() {
    return new Object[][]{
        {1, 1},
        {-1, 1},
        {1, -1},
        {1, 0},
        {-1, 0},
        {0, -1},
        {0, 1},
        {0, 0},
      // trailing comma is valid: see JLS 10.6 https://bit.ly/3C3QN0J
    };
  }

  @Test(dataProvider = "validCoordinates")
  public void testValidCoordinate(Integer x, Integer y) {
    Coordinate c = Coordinate.builder().x(x).y(y).build();
    persist(c);
    // has passed validation, if we reach this point.
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void testInvalidCoordinate() {
    testValidCoordinate(-1, -1);
  }
}
