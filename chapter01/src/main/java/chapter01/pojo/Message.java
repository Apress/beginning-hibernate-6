package chapter01.pojo;

import java.util.Objects;

public class Message {
  String text;

  public Message(String text) {
    setText(text);
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
    if (!(o instanceof Message)) return false;
    Message message = (Message) o;
    return Objects.equals(getText(), message.getText());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getText());
  }

  @Override
  public String toString() {
    return String.format("Message{text='%s'}", getText());
  }
}
