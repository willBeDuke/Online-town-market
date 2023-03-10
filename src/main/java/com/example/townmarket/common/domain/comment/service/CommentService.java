package com.example.townmarket.common.domain.comment.service;

import com.example.townmarket.common.domain.comment.dto.CommentRequestDto;
import com.example.townmarket.common.domain.comment.entity.Comment;
import com.example.townmarket.common.domain.user.entity.User;

public interface CommentService {

  void createComment(Long boardsId, CommentRequestDto commentRequestDto, User user);

  void updateComment(Long commentId, CommentRequestDto commentRequestDto,
      User user);

  void deleteComment(Long commentId, User user);

  Comment findCommentById(Long commentId);
}
