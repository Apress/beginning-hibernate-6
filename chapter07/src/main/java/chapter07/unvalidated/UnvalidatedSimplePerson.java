package chapter07.unvalidated;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UnvalidatedSimplePerson {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column
  String fname;
  @Column
  String lname;
  @Column
  Integer age;
}
