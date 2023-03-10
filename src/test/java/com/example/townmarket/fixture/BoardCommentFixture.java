package com.example.townmarket.fixture;

import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;

import com.example.townmarket.common.domain.board.dto.PagingBoardResponse;
import com.example.townmarket.common.domain.board.entity.Board.BoardSubject;
import com.example.townmarket.common.domain.comment.dto.CommentRequestDto;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class BoardCommentFixture {

  public static final PagingBoardResponse PAGING_BOARD_RESPONSE = PagingBoardResponse.builder().title("글제목")
      .subject(BoardSubject.동네소식).boardId(1L).username("user3").build();

  public static final Page<PagingBoardResponse> PAGING_BOARD_RESPONSE_PAGE = new PageImpl<>(
      Collections.singletonList(PAGING_BOARD_RESPONSE),
      PAGE_DTO.toPageable(), 1);

  public static  final CommentRequestDto COMMENT_REQUEST_DTO = new CommentRequestDto("댓글 생성");
  public static final Long BOARD_ID = 1L;
  public static final Long COMMENT_ID = 1L;

}
