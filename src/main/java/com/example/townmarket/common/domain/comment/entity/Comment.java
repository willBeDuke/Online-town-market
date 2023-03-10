package com.example.townmarket.common.domain.comment.entity;

import com.example.townmarket.common.TimeStamped;
import com.example.townmarket.common.domain.board.entity.Board;
import com.example.townmarket.common.domain.comment.dto.CommentRequestDto;
import com.example.townmarket.common.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends TimeStamped {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String comment;

  private String username;

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  @Builder
  public Comment(String comment, String username, User user, Board board) {
    this.comment = comment;
    this.username = username;
    this.user = user;
    this.board = board;
  }


  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */


  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  // 댓글 수정 로직
  public void update(CommentRequestDto commentRequestDto) {
    this.comment = commentRequestDto.getCommentContents();
  }

  public boolean checkCommentWriter(User user) {
    return this.user.getId().equals(user.getId());
  }
}
