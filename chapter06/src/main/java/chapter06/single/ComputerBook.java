package chapter06.single;

import javax.persistence.Entity;

@Entity(name="SingleCBook")
public class ComputerBook extends Book {
  String primaryLanguage;
}
