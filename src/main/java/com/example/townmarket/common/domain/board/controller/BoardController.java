package com.example.townmarket.common.domain.board.controller;

import static com.example.townmarket.common.domain.board.controller.BoardController.BOARD_API_URI;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_CREATED;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_DELETE;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_OK;

import com.example.townmarket.common.domain.board.dto.BoardRequestDto;
import com.example.townmarket.common.domain.board.dto.BoardResponseDto;
import com.example.townmarket.common.domain.board.dto.PagingBoardResponse;
import com.example.townmarket.common.domain.board.service.BoardService;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BOARD_API_URI)
@RequiredArgsConstructor
public class BoardController {

  public static final String BOARD_API_URI = "/boards";

  private final BoardService boardService;
  private final SetHttpHeaders httpHeaders;

  // 게시글 생성
  @PostMapping
  public ResponseEntity<StatusResponse> createBoard(
      @RequestBody BoardRequestDto boardRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    boardService.createBoard(boardRequestDto, userDetails.getUser());
    return RESPONSE_OK;
  }

  // 게시글 수정
  @PutMapping("/{boardId}")
  public ResponseEntity<StatusResponse> updateBoard(@PathVariable Long boardId,
      @RequestBody BoardRequestDto boardRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    boardService.updateBoard(boardId, boardRequestDto, userDetails.getUser());
    return RESPONSE_OK;
  }

  // 게시글 단건 조회, 댓글 목록으로 불러오게 추가(페이징)
  @GetMapping("/{boardId}")
  public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId) {
    return ResponseEntity.ok().headers(httpHeaders.setHeaderTypeJson())
        .body(boardService.getBoard(boardId));
  }

  // 게시글 전체 조회
  @GetMapping
  public ResponseEntity<Page<PagingBoardResponse>> getBoards(
      @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    return ResponseEntity.ok().headers(httpHeaders.setHeaderTypeJson())
        .body(boardService.getBoards(pageable));
  }

  // 게시물 삭제
  @DeleteMapping("{boardId}")
  public ResponseEntity<StatusResponse> deleteBoard(@PathVariable Long boardId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    boardService.deleteBoard(boardId, userDetails.getUser());
    return RESPONSE_DELETE;
  }
}
