package chapter13.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Audited
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;

  @Column(unique = true)
  String name;

  boolean active;

  @ElementCollection
  Set<String> groups;

  String description;

  public User(String name, boolean active) {
    this.name = name;
    this.active = active;
  }

  public void addGroups(String... groupSet) {
    if (getGroups() == null) {
      setGroups(new HashSet<>());
    }
    getGroups().addAll(Arrays.asList(groupSet));

  }
}
