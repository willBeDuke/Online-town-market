package com.example.townmarket.common.domain.board.service;

import com.example.townmarket.common.domain.board.dto.BoardRequestDto;
import com.example.townmarket.common.domain.board.dto.BoardResponseDto;
import com.example.townmarket.common.domain.board.dto.PagingBoardResponse;
import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.board.repository.BoardRepository;
import com.example.townmarket.common.domain.comment.dto.CommentResponseDto;
import com.example.townmarket.common.domain.product.repository.ProductRepository;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;


  // 게시글 생성
  @Override
  @Transactional
  public Board createBoard(BoardRequestDto boardRequestDto, User user) {
    Board board = Board.builder()
        .title(boardRequestDto.getTitle())
        .content(boardRequestDto.getContent())
        .subject(boardRequestDto.getSubject())
        .user(user)
        .build();
    return boardRepository.save(board);
  }

  // 게시글 수정
  @Override
  @Transactional
  public void updateBoard(Long boardId, BoardRequestDto boardRequestDto,
      User user) {
    Board board = findBoardById(boardId);
    if (!board.checkBoardWriter(user)) {
      throw new IllegalArgumentException("본인 게시글이 아닙니다");
    }
    board.update(boardRequestDto);
  }

  // 게시글 전체 조회
  @Override
  @Transactional(readOnly = true)
  public Page<PagingBoardResponse> getBoards(Pageable pageable) {
    Page<Board> boards = boardRepository.findAll(pageable);
    return boards.map(PagingBoardResponse::new);
  }


  // 게시글 단건 조회
  @Override
  @Transactional(readOnly = true)
  public BoardResponseDto getBoard(Long boardId) {
    Board board = findBoardById(boardId);
    return BoardResponseDto.builder()
        .boardId(boardId)
        .title(board.getTitle())
        .content(board.getContent())
        .subject(board.getSubject())
        .comments(
            board.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList()))
        .username(board.getUser().getUsername())
        .createdAt(board.getCreatedAt())
        .modifiedAt(board.getModifiedAt())
        .build();
  }

  // 게시글 삭제
  @Override
  @Transactional
  public void deleteBoard(Long boardId, User user) {
    Board board = findBoardById(boardId);
    if (!board.checkBoardWriter(user)) {
      throw new IllegalArgumentException("본인의 게시글이 아닙니다.");
    }
    boardRepository.deleteById(board.getId());

  }

  // 중복 로직 메서드 분리
  @Override
  @Transactional(readOnly = true)
  public Board findBoardById(Long boardId) {
    return boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
  }
}
