package com.example.townmarket.common.domain.board.dto;

import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.board.entity.Board.BoardSubject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingBoardResponse {

  private String title;
  private BoardSubject subject;

  private long boardId;

  private String username;

  @Builder
  public PagingBoardResponse(Board board) {
    this.title = board.getTitle();
    this.subject = board.getSubject();
    this.boardId = board.getId();
    this.username = board.getUser().getUsername();
  }
}
