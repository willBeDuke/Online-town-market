package com.example.townmarket.common.domain.comment.service;

import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.board.service.BoardService;
import com.example.townmarket.common.domain.comment.dto.CommentRequestDto;
import com.example.townmarket.common.domain.comment.entity.Comment;
import com.example.townmarket.common.domain.comment.repository.CommentRepository;
import com.example.townmarket.common.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  private final BoardService boardService;


  // 댓글 생성
  @Override
  @Transactional
  public void createComment(Long boardsId, CommentRequestDto commentRequestDto, User user) {
    Board board = boardService.findBoardById(boardsId);
    Comment comment = Comment.builder()
        .comment(commentRequestDto.getCommentContents())
        .username(user.getUsername())
        .user(user)
        .board(board)
        .build();
    commentRepository.save(comment);
  }

  // 댓글 수정
  @Override
  @Transactional
  public void updateComment(Long commentId, CommentRequestDto commentRequestDto,
      User user) {
    Comment comment = findCommentById(commentId);

    if (!comment.checkCommentWriter(user)) { // comment 안에 서비스 구현
      throw new IllegalArgumentException("수정이 완료되지 않았습니다.");
    }
    comment.update(commentRequestDto);
  }

  // 댓글 삭제
  @Override
  @Transactional
  public void deleteComment(Long commentId, User user) {
    Comment comment = findCommentById(commentId);

    if (!comment.checkCommentWriter(user)) {
      throw new IllegalArgumentException("댓글 삭제가 불가능합니다.");
    }
    commentRepository.deleteById(comment.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public Comment findCommentById(Long commentId) {
    return commentRepository.findById(commentId).orElseThrow(() ->
        new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
  }
}
