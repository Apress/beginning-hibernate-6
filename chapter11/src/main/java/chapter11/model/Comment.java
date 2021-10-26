package chapter11.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;

  @Column(nullable = false)
  @Lob
  String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  Post post;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  LocalDateTime createDate;

  @Override
  public String toString() {
    return "Comment{" +
      "id=" + id +
      ", content='" + content + '\'' +
      ", createDate=" + createDate +
      '}';
  }
}
