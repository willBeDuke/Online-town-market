package com.example.townmarket.common.domain.user.entity;

import com.example.townmarket.common.domain.user.dto.ProfileRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Profile {

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */

  @Column(unique = true)
  private String nickName;

  @Column
  private String img_url = "https://img.freepik.com/premium-vector/anonymous-user-circle-icon-vector-illustration-flat-style-with-long-shadow_520826-1931.jpg";

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  @Builder
  public Profile(String nickName, String img_url) {
    this.nickName = nickName;
    this.img_url = img_url;
  }


  public Profile(String nickName) {
    this.nickName = nickName;
  }


  public void update(ProfileRequestDto requestDto) {
    this.nickName = requestDto.getNickname();
    this.img_url = requestDto.getImg_url();
  }

  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */

  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */

}
