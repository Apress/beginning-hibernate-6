package chapter11.dto;

import lombok.Data;

@Data
public class UserDTO {
  int id;
  String name;
  boolean active;

  public UserDTO() {
  }

  public UserDTO(
    int id,
    String name,
    boolean active
  ) {
    this.id = id;
    this.name = name;
    this.active = active;
  }
}
