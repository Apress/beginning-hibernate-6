package chapter11.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.OrderBy;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@FilterDefs({
  @FilterDef(
    name = "byTerm",
    parameters = @ParamDef(name = "term", type = "string")),
  @FilterDef(
    name = "byName",
    parameters = @ParamDef(name = "name", type = "string")
  )
})
@Filters({
  @Filter(name = "byTerm",
    condition = "title like :term"),
  @Filter(name = "byName",
    condition = "user_id = (select u.id from User as u where u.name=:name)"),
})
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;

  @Column(nullable = false)
  String title;

  @Column(nullable = false)
  @Lob
  String content;

  @ManyToOne
  User user;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  @OrderBy("createDate")
  List<Comment> comments = new ArrayList<>();

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  LocalDateTime createDate;

  @Override
  public String toString() {
    return "Post{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", content='" + content + '\'' +
      ", user=" + user +
      ", createDate=" + createDate +
      '}';
  }

}
