package com.example.townmarket.common.domain.comment.dto;

import com.example.townmarket.common.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
  private long commentId;
  private String username;
  private String comment;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  public CommentResponseDto(Comment comment) {
    this.commentId = comment.getId();
    this.username = comment.getUsername();
    this.comment = comment.getComment();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();
  }
}
