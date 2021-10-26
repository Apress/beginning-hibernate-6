package chapter06.joined;

import javax.persistence.Entity;

@Entity(name="JoinedCBook")
public class ComputerBook extends Book{
  String primaryLanguage;
}
