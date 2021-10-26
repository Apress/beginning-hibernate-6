package chapter04.broken;

import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ImplicitRelationshipTest {
  @Test
  public void testImpliedRelationship() {
    Long emailId;
    Long messageId;

    Session session = SessionUtil.getSession();
    Transaction tx = session.beginTransaction();

    Email email = new Email("Inverse Email");
    Message message = new Message("Inverse Message");

    email.setMessage(message);
    // message.setEmail(email);

    session.save(email);
    session.save(message);

    emailId = email.getId();
    messageId = message.getId();

    tx.commit();
    session.close();

    assertEquals(email.getSubject(), "Inverse Email");
    assertEquals(message.getContent(), "Inverse Message");
    assertNotNull(email.getMessage());
    assertNull(message.getEmail());

    session = SessionUtil.getSession();
    tx = session.beginTransaction();
    email = session.get(Email.class, emailId);
    System.out.println(email);
    message = session.get(Message.class, messageId);
    System.out.println(message);

    tx.commit();
    session.close();

    assertNotNull(email.getMessage());
    assertNull(message.getEmail());
  }
}
