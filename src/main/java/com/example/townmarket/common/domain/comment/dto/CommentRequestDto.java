package com.example.townmarket.common.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentRequestDto {
  private String commentContents;

  public CommentRequestDto(String commentContents) {
    this.commentContents = commentContents;
  }
}

