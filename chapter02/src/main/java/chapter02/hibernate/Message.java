package chapter02.hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Message {
    Long id;
    String text;

    public Message() {
    }

    public Message(String text) {
        this();
        setText(text);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
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
        return Objects.equals(getId(), message.getId())
            && Objects.equals(getText(), message.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText());
    }

    @Override
    public String toString() {
        return String.format("Message{id=%d,text='%s'}",
            getId(),
            getText());
    }
}
