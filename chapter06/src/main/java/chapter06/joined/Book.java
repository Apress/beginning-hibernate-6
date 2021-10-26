package chapter06.joined;

import javax.persistence.*;

@Entity(name="JoinedBook")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {
  // contents common to all Books go here
  @Id
  Long bookId;
  String title;
  // imagine many more
}
