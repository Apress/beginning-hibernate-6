package chapter06.perclass;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="PerClassBook")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Book {
  // contents common to all Books go here
  @Id
  Long bookId;
  String title;
  // imagine many more
}
