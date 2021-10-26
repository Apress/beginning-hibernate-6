//tag::preamble[]
package chapter04.mapped;

import javax.persistence.*;

@Entity(name = "Message2")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @Column
  String content;

  @OneToOne
  Email email;

  public Message() {
  }

  public Message(String content) {
    setContent(content);
  }
  //end::preamble[]

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  //tag::postlude[]
  @Override
  public String toString() {
    // note use of email.subject because otherwise properly constructed
    // relationships would cause an endless loop that never ends
    // and therefore runs endlessly.
    return String.format(
        "Message{id=%d, content='%s', email.subject='%s'}",
        id,
        content,
        (email != null ? email.getSubject() : "null")
    );
  }
}
//end::postlude[]
