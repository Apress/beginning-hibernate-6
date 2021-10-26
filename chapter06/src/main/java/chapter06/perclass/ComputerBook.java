package chapter06.perclass;

import javax.persistence.Entity;

@Entity(name="PerClassCBook")
public class ComputerBook extends Book {
  String primaryLanguage;
}
