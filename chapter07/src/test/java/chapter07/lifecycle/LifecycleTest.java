package chapter07.lifecycle;

import com.autumncode.jpa.util.JPASessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class LifecycleTest {
  @Test
  public void persistenceErrorTests() {
    testPersistence("value1", FailingEntity.FailureStatus.NOFAILURE);
    testPersistence("value2", FailingEntity.FailureStatus.PREPERSIST);
    testPersistence("value3", FailingEntity.FailureStatus.POSTPERSIST);
  }

  @Test
  public void prepersistException() {
    Session session = JPASessionUtil.getSession("chapter07");

    Transaction tx = session.beginTransaction();
    session.createQuery("delete from FailingEntity").executeUpdate();
    tx.commit();

    tx = session.beginTransaction();
    FailingEntity fe = new FailingEntity();
    fe.setValue("FailingEntity");
    fe.setFailureStatus(FailingEntity.FailureStatus.PREPERSIST);
    try {
      session.persist(fe);
    } catch (Throwable ignored) {
      ignored.printStackTrace();
    }
    tx.rollback();
    session.close();

    session = JPASessionUtil.getSession("chapter07");
    tx = session.beginTransaction();
    List e = session.createQuery("from FailingEntity fe").list();
    System.out.println(e);
    tx.commit();
    session.close();
  }

  public void testPersistence(String value,
                              FailingEntity.FailureStatus status) {
    Session session = JPASessionUtil.getSession("chapter07");
    Transaction tx = session.beginTransaction();

    FailingEntity failingEntity = new FailingEntity();
    failingEntity.setValue(value);
    failingEntity.setFailureStatus(status);
    try {
      session.persist(failingEntity);
      Integer id = failingEntity.getId();
      tx.commit();
      session.close();
      System.out.println(failingEntity);
      session = JPASessionUtil.getSession("chapter07");
      tx = session.beginTransaction();
      FailingEntity fe = session.load(FailingEntity.class, id);
      assertEquals(fe, failingEntity);
      session.delete(fe);
      tx.commit();
      session.close();
    } catch (RuntimeException exception) {
      if (FailingEntity.FailureStatus.NOFAILURE.equals(status)) {
        throw exception;
      }
      tx.rollback();
      session.close();
    }
  }

  @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "postload failure")
  public void testPostLoad() {
    Integer id;
    Session session = JPASessionUtil.getSession("chapter07");
    Transaction tx = session.beginTransaction();

    FailingEntity failingEntity = new FailingEntity();
    failingEntity.setValue("postload");
    failingEntity.setFailureStatus(FailingEntity.FailureStatus.POSTLOAD);

    session.persist(failingEntity);
    tx.commit();
    session.close();

    session = JPASessionUtil.getSession("chapter07");
    tx = session.beginTransaction();
    FailingEntity e = session
        .byId(FailingEntity.class)
        .load(failingEntity.getId());
    System.out.println(e);
    tx.commit();
    session.close();
  }
}
