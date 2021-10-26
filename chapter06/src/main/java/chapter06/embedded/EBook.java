package chapter06.embedded;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class EBook {
  @Id
  @GeneratedValue
  Long id;
  String name;
  @ElementCollection
  Set<Author> authors;
}
