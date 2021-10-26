package chapter11.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;

  @Column(unique = true, nullable = false)
  String name;
  boolean active;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @OrderBy("createDate")
  List<Post> posts = new ArrayList<>();

  public User(String name, boolean active) {
    this.name = name;
    this.active = active;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", active=" + active +
      '}';
  }
}
