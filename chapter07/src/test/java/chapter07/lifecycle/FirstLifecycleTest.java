package chapter07.lifecycle;

import com.autumncode.jpa.util.JPASessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Reporter;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FirstLifecycleTest {
  @Test
  public void testLifecycle() {
    Integer id;
    LifecycleThing thing1, thing2, thing3;
    try (Session session = JPASessionUtil.getSession("chapter07")) {
      Transaction tx = session.beginTransaction();
      thing1 = new LifecycleThing();
      thing1.setName("Thing 1");

      session.save(thing1);
      id = thing1.getId();
      System.out.println(thing1);
      tx.commit();
    }

    try (Session session = JPASessionUtil.getSession("chapter07")) {
      Transaction tx = session.beginTransaction();
      thing2 = session
          .byId(LifecycleThing.class)
          .load(-1);
      assertNull(thing2);

      Reporter.log("attempted to load nonexistent reference");

      thing2 = session.byId(LifecycleThing.class)
          .getReference(id);
      assertNotNull(thing2);
      assertEquals(thing1, thing2);

      thing2.setName("Thing 2");

      tx.commit();
    }
    try (Session session = JPASessionUtil.getSession("chapter07")) {
      Transaction tx = session.beginTransaction();

      thing3 = session
          .byId(LifecycleThing.class)
          .getReference(id);
      assertNotNull(thing3);
      assertEquals(thing2, thing3);

      session.delete(thing3);

      tx.commit();
    }
    assertEquals(LifecycleThing.lifecycleCalls.nextClearBit(0), 7);
  }
}
