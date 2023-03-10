package com.example.townmarket.common.domain.board.dto;


import com.example.townmarket.common.domain.board.entity.Board.BoardSubject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestDto {

  private String title;
  private String content;
  private BoardSubject subject;

}
