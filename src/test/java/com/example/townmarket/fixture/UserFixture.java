package com.example.townmarket.fixture;

import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;

import com.example.townmarket.admin.dto.PagingUserResponse;
import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.user.dto.DuplicateCheckRequestDto;
import com.example.townmarket.common.domain.user.dto.DuplicateCheckResponseDto;
import com.example.townmarket.common.domain.user.dto.LoginRequestDto;
import com.example.townmarket.common.domain.user.dto.PasswordUpdateRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileResponseDto;
import com.example.townmarket.common.domain.user.dto.RegionUpdateRequestDto;
import com.example.townmarket.common.domain.user.dto.SignupRequestDto;
import com.example.townmarket.common.domain.user.entity.Profile;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.enums.RoleEnum;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class UserFixture {

  public static final Profile PROFILE = Profile.builder().nickName("nickname").img_url("img_url")
      .build();

  public static final Address ADDRESS = Address.builder().address("경기도").address2("고양시").address3("일산서구").build();

  public static final User USER1 = User.builder()
      .id(1L)
      .username("username1")
      .password("Password!2")
      .email("username1@google.com")
      .role(RoleEnum.MEMBER)
      .profile(PROFILE)
      .address(Collections.singletonList(ADDRESS))
      .build();

  public static final SignupRequestDto SIGNUP_REQUEST_DTO =
      SignupRequestDto.builder()
          .username("username1")
          .password("Password!2")
          .email("xxx0011@gmail.com")
          .nickname("nickname1")
          .img_url("asdassdas.com")
          .address1("고양시")
          .address2("일산서구")
          .address3("OO동")
          .build();

  public static final LoginRequestDto LOGIN_REQUEST_DTO =
      LoginRequestDto.builder()
          .username("username1")
          .password("Password1!")
          .build();

  public static final PasswordUpdateRequestDto PASSWORD_UPDATE_REQUEST_DTO =
      PasswordUpdateRequestDto.builder()
          .password("PassWord1!")
          .build();

  public static final RegionUpdateRequestDto REGION_UPDATE_REQUEST_DTO =
      RegionUpdateRequestDto.builder()
          .region("서울")
          .build();

  public static final ProfileResponseDto PROFILE_RESPONSE_DTO =
      ProfileResponseDto.builder()
          .img_url("img_url")
          .nickname("nickname1")
          .build();

  public static final DuplicateCheckRequestDto DUPLICATE_CHECK_REQUEST_DTO =
      DuplicateCheckRequestDto.builder()
          .duplicateField("username")
          .content("중복 확인을 위한 내용")
          .build();

  public static final PagingUserResponse PAGING_USER_RESPONSE1 =
      new PagingUserResponse(USER1);

  public static final Page<PagingUserResponse> PAGING_USER_RESPONSES =
      new PageImpl<>(Collections.singletonList(PAGING_USER_RESPONSE1), PAGE_DTO.toPageable(), 1);


  public static final DuplicateCheckResponseDto DUPLICATE_CHECK_RESPONSE_DTO =
      new DuplicateCheckResponseDto(false);

  public static final Long MEMBER_UNIQUE_ID = 1L;


}
