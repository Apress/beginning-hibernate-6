package chapter06.embedded;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User {
  @Id
  @GeneratedValue
  Long id;
  String name;
  // this is... not wise from a security perspective
  String password;
  @ElementCollection
  List<String> passwordHints;
}
