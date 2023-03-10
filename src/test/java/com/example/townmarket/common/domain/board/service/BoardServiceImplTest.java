package com.example.townmarket.common.domain.board.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.board.dto.BoardRequestDto;
import com.example.townmarket.common.domain.board.dto.BoardResponseDto;
import com.example.townmarket.common.domain.board.dto.PagingBoardResponse;
import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.board.repository.BoardRepository;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

  @Mock
  BoardRepository boardRepository;

  @InjectMocks
  BoardServiceImpl boardService;

  @Test
  @DisplayName("게시글 생성 성공 테스트")
  void createBoard() {
    BoardRequestDto requestDto = BoardRequestDto.builder()
        .title("title1")
        .content("content1")
        .subject(Board.BoardSubject.공지사항)
        .build();

    User user = mock(User.class);

    Board board = Board.builder()
        .title(requestDto.getTitle())
        .content(requestDto.getContent())
        .subject(Board.BoardSubject.공지사항)
        .build();

    given(boardRepository.save(any())).willReturn(board);

    //when
    Board savedBoard = boardService.createBoard(requestDto, user);

    // then
    assertThat(savedBoard.getTitle()).isEqualTo(requestDto.getTitle());
    assertThat(savedBoard.getContent()).isEqualTo(requestDto.getContent());

  }

  @Test
  @DisplayName("게시글 업데이트 성공 테스트")
  void updateBoard() {
    // given
    User user = mock(User.class);
    Board board = Board.builder()
        .title("test-title")
        .content("test-content")
        .subject(Board.BoardSubject.동네소식)
        .user(user)
        .build();

    BoardRequestDto boardRequestDto = BoardRequestDto.builder()
        .title("after-title")
        .content("after-content")
        .subject(Board.BoardSubject.공지사항)
        .build();

    when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

    // when
    boardService.updateBoard(board.getId(), boardRequestDto, user);

    // then
    assertThat(board.getTitle()).isEqualTo(boardRequestDto.getTitle());
    assertThat(board.getContent()).isEqualTo(boardRequestDto.getContent());
    assertThat(board.getSubject()).isEqualTo(boardRequestDto.getSubject());

  }

  @Test
  @DisplayName("게시글 목록 불러오기 성공 테스트")
  void getBoards() {
    // given
    Pageable pageable = mock(Pageable.class);

    when(boardRepository.findAll(pageable)).thenReturn(Page.empty());

    // when
    Page<PagingBoardResponse> pagingProductResponse = boardService.getBoards(pageable);

    // then
    assertThat(pagingProductResponse).isNotNull();
  }

  @Test
  @DisplayName("게시글 단건 조회 성공 테스트")
  void getBoard() {

    // given
    User user = mock(User.class);
    Board board = Board.builder()
        .title("test-title")
        .content("test-content")
        .user(user)
        .build();

    when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

    // when
    BoardResponseDto boardResponse = boardService.getBoard(board.getId());

    // then
    assertThat(boardResponse.getContent()).isEqualTo(board.getContent());
    assertThat(boardResponse.getTitle()).isEqualTo(board.getTitle());
  }

  @Test
  @DisplayName("게시글 삭제 성공 테스트")
  void deleteBoard() {
    //given
    Board board = mock(Board.class);
    User user = mock(User.class);

    when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
    when(Optional.of(board).get().checkBoardWriter(user)).thenReturn(true);

    //when
    boardService.deleteBoard(board.getId(),user);

    //then
    verify(boardRepository, times(1)).deleteById(board.getId());

  }

  @Test
  @DisplayName("게시글 리포지토리 조회 후 게시글 반환 성공 테스트")
  void findBoardById() {
    //given
    Board board = mock(Board.class);

    when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

    //when
    Board boardResultFindById = boardService.findBoardById(board.getId());

    //then
    assertThat(boardResultFindById).isEqualTo(board);
  }

}
