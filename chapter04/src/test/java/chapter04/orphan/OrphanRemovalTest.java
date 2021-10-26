package chapter04.orphan;

import com.autumncode.hibernate.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class OrphanRemovalTest {
  @Test
  public void orphanRemovalTest() {
    Long id = createLibrary();

    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      Library library = session.load(Library.class, id);
      assertEquals(library.getBooks().size(), 3);

      library.getBooks().remove(0);
      assertEquals(library.getBooks().size(), 2);

      tx.commit();
    }

    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      Library l2 = session.load(Library.class, id);
      assertEquals(l2.getBooks().size(), 2);

      Query<Book> query = session
        .createQuery("from Book b", Book.class);
      List<Book> books = query.list();
      assertEquals(books.size(), 2);

      tx.commit();
    }
  }

  @Test
  public void deleteLibrary() {
    Long id = createLibrary();
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      Library library = session.load(Library.class, id);
      assertEquals(library.getBooks().size(), 3);
      session.delete(library);
      tx.commit();
    }

    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();
      Library library = session.get(Library.class, id);
      assertNull(library);
      List<Book> books=session
        .createQuery("from Book b", Book.class)
        .list();
      assertEquals(books.size(), 0);
    }
  }

  private Long createLibrary() {
    Library library = null;
    try (Session session = SessionUtil.getSession()) {
      Transaction tx = session.beginTransaction();

      library = new Library();
      library.setName("orphanLib");
      session.save(library);

      Book book = new Book();
      book.setLibrary(library);
      book.setTitle("book 1");
      session.save(book);
      library.getBooks().add(book);

      book = new Book();
      book.setLibrary(library);
      book.setTitle("book 2");
      session.save(book);
      library.getBooks().add(book);

      book = new Book();
      book.setLibrary(library);
      book.setTitle("book 3");
      session.save(book);
      library.getBooks().add(book);

      tx.commit();
    }

    return library.getId();
  }
}
