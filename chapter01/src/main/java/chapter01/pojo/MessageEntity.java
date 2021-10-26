package chapter01.pojo;

import java.util.Objects;

public class MessageEntity {
  Long id;
  String text;

  public MessageEntity() {
  }

  public MessageEntity(Long id, String text) {
    this();
    setId(id);
    setText(text);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MessageEntity)) return false;
    MessageEntity message = (MessageEntity) o;
    return Objects.equals(getId(), message.getId())
        && Objects.equals(getText(), message.getText());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getText());
  }

  @Override
  public String toString() {
    return String.format("MessageEntity{id=%d,text='%s'}",
        getId(),
        getText());
  }
}
