package com.example.townmarket.common.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {

  @Size(min = 4, max = 12, message = "최소4자, 최대12자")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "소문자와 숫자를 포함하여 4자 이상 12자 이하로 적어주세요.")
  @NotEmpty(message = "아이디를 입력해주세요.")
  private String username;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$", message = "소문자와 대문자 특수문자 그리고 숫자를 포함하여 8자 이상 15자 이하로 적어주세요.")
  @NotEmpty(message = "비밀번호를 입력해주세요.")
  private String password;

  //  @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$", message = "숫자와 - 를 포함하여 휴대폰 번호를 적어주세요.")
//  @NotEmpty(message = "휴대폰 번호를 입력해주세요.")
//  private String phoneNumber;
  @NotEmpty
  private String address1;
  @NotEmpty
  private String address2;
  @NotEmpty
  private String address3;
  @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
  @NotEmpty(message = "본인 인증 가능한 이메일을 입력해주세요.")
  private String email;
  @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "소문자와 숫자를 포함하여 4자 이상 12자 이하로  적어주세요.")
  private String nickname;

  private String img_url;
  //  @NotEmpty(message = "거래할 지역의 읍,면,동 까지 적어주세요.")



}
