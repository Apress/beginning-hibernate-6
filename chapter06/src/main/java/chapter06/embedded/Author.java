package chapter06.embedded;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Author {
  String name;
  LocalDate dateOfBirth;
}
