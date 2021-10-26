package chapter06.single;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="SingleBook")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Book {
  // contents common to all Books go here
  @Id
  Long bookId;
  String title;
  // imagine many more
}
