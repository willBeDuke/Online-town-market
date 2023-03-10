package com.example.townmarket.common.domain.board.service;

import com.example.townmarket.common.domain.board.dto.BoardRequestDto;
import com.example.townmarket.common.domain.board.dto.BoardResponseDto;
import com.example.townmarket.common.domain.board.dto.PagingBoardResponse;
import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

  Board createBoard(BoardRequestDto boardRequestDto, User user);

  void updateBoard(Long boardId, BoardRequestDto boardRequestDto, User user);

  BoardResponseDto getBoard(Long boardId);

  Page<PagingBoardResponse> getBoards(Pageable pageable);

  void deleteBoard(Long boardId, User user);

  Board findBoardById(Long boardsId);
}
