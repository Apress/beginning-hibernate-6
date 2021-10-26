package chapter07.validator;

import chapter07.unvalidated.UnvalidatedSimplePerson;
import chapter07.validated.ValidatedPerson;
import com.autumncode.hibernate.util.SessionUtil;

import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class ValidatorTest {
  private ValidatedPerson persist(ValidatedPerson person) {
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      session.persist(person);
      tx.commit();
    }
    return person;
  }

  @Test
  public void createUnvalidatedUnderagePerson() {
    Long id = null;
    try (Session session = SessionUtil.getSession()) {
      Transaction transaction = session.beginTransaction();

      UnvalidatedSimplePerson person = new UnvalidatedSimplePerson();
      person.setAge(12); // underage for system
      person.setFname("Johnny");
      person.setLname("McYoungster");

      // this succeeds because the UnvalidatedSimplePerson
      // has no validation in place.
      session.persist(person);
      id = person.getId();
      transaction.commit();
    }
  }

  @Test
  public void createValidPerson() {
    persist(ValidatedPerson.builder()
        .age(15)
        .fname("Johnny")
        .lname("McYoungster").build());
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createValidatedUnderagePerson() {
    persist(ValidatedPerson.builder()
        .age(12)
        .fname("Johnny")
        .lname("McYoungster").build());
    fail("Should have failed validation");
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createValidatedPoorFNamePerson2() {
    persist(ValidatedPerson.builder()
        .age(14)
        .fname("J")
        .lname("McYoungster2").build());
    fail("Should have failed validation");
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createValidatedNoFNamePerson() {
    persist(ValidatedPerson.builder()
        .age(14)
        .lname("McYoungster2").build());
    fail("Should have failed validation");
  }

}
