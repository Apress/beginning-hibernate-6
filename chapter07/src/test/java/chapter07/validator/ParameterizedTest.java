package chapter07.validator;

import chapter07.unvalidated.UnvalidatedSimplePerson;
import chapter07.validated.ValidatedPerson;
import com.autumncode.hibernate.util.SessionUtil;
import lombok.val;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.IExpectedExceptionsHolder;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;

import static org.testng.Assert.fail;

public class ParameterizedTest {
  private ValidatedPerson persist(ValidatedPerson person) {
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      session.persist(person);
      tx.commit();
    }
    return person;
  }

  @DataProvider
  Object[][] provider() {
    return new Object[][]{
        {"Johnny", "McYoungster", 15, false},
        {"Johnny", "McYoungster", 12, true},
        {"J", "McYoungster", 14, true},
        {"Johnny", "M", 14, true},
        {"Johnny", null, 14, true},
    };
  }

  @Test(dataProvider = "provider")
  void testValidations(String fname, String lname, Integer age, boolean expectException) {
    try {
      val builder=ValidatedPerson
          .builder()
          .age(age)
          .fname(fname);
      if(lname!=null) {
        builder.lname(lname);
      }
      persist(builder.build());
      if (expectException) {
        fail("should have caught an exception");
      }
    } catch (Exception ex) {
      if (!expectException) {
        fail("expected an exception");
      }
    }
  }
}
