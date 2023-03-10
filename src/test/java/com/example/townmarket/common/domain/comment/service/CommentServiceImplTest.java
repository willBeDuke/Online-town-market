package com.example.townmarket.common.domain.comment.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.board.service.BoardService;
import com.example.townmarket.common.domain.comment.dto.CommentRequestDto;
import com.example.townmarket.common.domain.comment.entity.Comment;
import com.example.townmarket.common.domain.comment.repository.CommentRepository;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

  @Mock
  CommentRepository commentRepository;

  @Mock
  BoardService boardService;

  @InjectMocks
  CommentServiceImpl commentService;

  @Test
  @DisplayName("댓글 생성 성공")
  void createComment() {
    // given
    CommentRequestDto commentRequestDto = mock(CommentRequestDto.class);
    Board board = mock(Board.class);
    User user = mock(User.class);

    when(boardService.findBoardById(board.getId())).thenReturn(board);

    // when
    commentService.createComment(board.getId(), commentRequestDto, user);

    // then
    verify(commentRepository, times(1)).save(isA(Comment.class));
  }

  @Test
  @DisplayName("댓글 수정 성공")
  void updateComment() {
    // given
    User user = mock(User.class);
    Comment comment = mock(Comment.class);
    CommentRequestDto commentRequestDto = mock(CommentRequestDto.class);

    when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
    when(Optional.of(comment).get().checkCommentWriter(user)).thenReturn(true);

    // when
    commentService.updateComment(comment.getId(), commentRequestDto, user);

    // then
    verify(comment, times(1)).update(commentRequestDto);

  }

  @Test
  @DisplayName("댓글 삭제 성공")
  void deleteComment() {
    // given
    User user = mock(User.class);
    Comment comment = mock(Comment.class);

    when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
    when(Optional.of(comment).get().checkCommentWriter(user)).thenReturn(true);

    // when
    commentService.deleteComment(comment.getId(), user);

    // then
    verify(commentRepository, times(1)).deleteById(comment.getId());

  }

  @Test
  @DisplayName("댓글 레포지토리 조회 후 댓글 반환 성공 테스트")
  void findCommentById() {
    // given
    Comment comment = mock(Comment.class);
    when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

    // when
    Comment resultCommentFindById = commentService.findCommentById(comment.getId());

    // then
    assertThat(resultCommentFindById).isEqualTo(comment);
  }
}
