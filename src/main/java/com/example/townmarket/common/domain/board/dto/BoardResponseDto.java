package com.example.townmarket.common.domain.board.dto;

import com.example.townmarket.common.domain.board.entity.Board.BoardSubject;
import com.example.townmarket.common.domain.comment.dto.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponseDto {

  private Long boardId;
  private String title;

  private String content;

  private BoardSubject subject;

  private List<CommentResponseDto> comments;

  private String username;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

}
