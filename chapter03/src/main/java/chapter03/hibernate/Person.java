//tag::boilerplate1[]
package chapter03.hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Person {
  @Column(unique = true)
  private String name;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Person() {
  }

  //end::boilerplate1[]
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person person = (Person) o;
    return Objects.equals(getName(), person.getName()) && Objects.equals(getId(), person.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getId());
  }

  //tag::boilerplate2[]
}
//end::boilerplate2[]
