package chapter08;

import chapter08.model.Publisher;
import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.PessimisticLockException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.persistence.OptimisticLockException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class DeadlockExample {
  Logger logger= LoggerFactory.getLogger(this.getClass());

  private Long createPublisher(Session session, String name) {
    Publisher publisher = new Publisher();
    publisher.setName(name);
    session.save(publisher);
    return publisher.getId();
  }

  private void updatePublishers(String prefix, Long... ids) {
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      for (Long id : ids) {
        Thread.sleep(300);
        Publisher publisher = session
            .byId(Publisher.class)
            .load(id);
        publisher.setName(prefix + " " + publisher.getName());
      }
      tx.commit();
    } catch (OptimisticLockException e) {
      logger.error("lock exception with prefix "+ prefix);
    } catch(InterruptedException ignored) {
    }
  }

  @Test
  public void showDeadlock() throws InterruptedException {
    Long publisherAId;
    Long publisherBId;

    //clear out old data and populate tables
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      session
          .createQuery("delete from Publisher")
          .executeUpdate();

      publisherAId = createPublisher(session, "A");
      publisherBId = createPublisher(session, "B");
      tx.commit();
    }

    ExecutorService executor = Executors.newFixedThreadPool(2);
    executor.submit(
        () -> updatePublishers("session1", publisherAId, publisherBId));
    executor.submit(
        () -> updatePublishers("session2", publisherBId, publisherAId));
    executor.shutdown();

    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
      executor.shutdownNow();
      if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        System.out.println("Executor did not terminate");
      }
    }
    try (Session session = SessionUtil.getSession()) {
      Query<Publisher> query = session.createQuery(
          "from Publisher p order by p.name",
          Publisher.class
      );
      String result = query
          .list()
          .stream()
          .map(Publisher::getName)
          .collect(Collectors.joining(","));
      assertEquals(result, "A,B");
    }
  }
}
