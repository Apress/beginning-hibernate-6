package chapter11.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
  int id;
  String user;
  String title;
  String content;
  List<CommentDTO> comments=List.of();
  LocalDateTime createdDate;
}
