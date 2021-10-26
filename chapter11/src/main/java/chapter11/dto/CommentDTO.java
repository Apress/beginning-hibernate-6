package chapter11.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
  String user;
  String content;
  LocalDateTime createdDate;

}
