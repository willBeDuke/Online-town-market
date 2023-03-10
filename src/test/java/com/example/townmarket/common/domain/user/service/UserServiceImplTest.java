package com.example.townmarket.common.domain.user.service;

import static com.example.townmarket.fixture.UserFixture.SIGNUP_REQUEST_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.address.service.AddressServiceImpl;
import com.example.townmarket.common.domain.user.dto.LoginRequestDto;
import com.example.townmarket.common.domain.user.dto.PasswordUpdateRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileResponseDto;
import com.example.townmarket.common.domain.user.dto.SignupRequestDto;
import com.example.townmarket.common.domain.user.entity.Profile;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import com.example.townmarket.common.jwtUtil.JwtUtil;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Mock
  JwtUtil jwtUtil;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  AddressServiceImpl addressService;

  @InjectMocks
  UserServiceImpl userService;


  @Test
  @DisplayName("회원가입 성공 테스트")
  void signup() {
    //given
    SignupRequestDto requestDto = SignupRequestDto.builder()
        .username("username1")
        .password("password")
        .email("abcd@naver.com")
        .nickname("김채원르세라핌원탑")
        .address1("서울특별시")
        .address2("강남구")
        .address3("압구정동")
        .build();

    User user = mock(User.class);

    //when
    userService.signup(requestDto);

    //then
    verify(userRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("로그인 성공 테스트")
  void login() {
    // given

    LoginRequestDto requestDto = LoginRequestDto.builder()
        .username("username1")
        .password("password")
        .build();

    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    Profile profile = new Profile(SIGNUP_REQUEST_DTO.getNickname());
    User user = new User(username, passwordEncoder.encode(password), profile);

    given(userRepository.findByUsername(username))
        .willReturn(Optional.of(user));
    given(!passwordEncoder.matches(password, user.getPassword())).willReturn(true);

    MockHttpServletResponse servletResponse = new MockHttpServletResponse();

    // when
    userService.login(servletResponse, requestDto);
    String token = jwtUtil.createAccessToken(username, user.getRole());
    servletResponse.addHeader("Authorization", token);
  }


  @Test
  @DisplayName("비밀번호 수정 성공 테스트")
  @Transactional
  void updateUser() {
    SignupRequestDto request = SignupRequestDto.builder()
        .username("username1")
        .password("password")
        .build();

    PasswordUpdateRequestDto requestDto = PasswordUpdateRequestDto.builder()
        .password("pass")
        .build();
    String password = passwordEncoder.encode(requestDto.getPassword());
    User newUser = User.builder()
        .username(request.getUsername()).password(password)
        .build();

    given(userRepository.findByUsername(newUser.getUsername()))
        .willReturn(Optional.of(newUser));

    // when
    userService.updateUser(newUser.getUsername(), requestDto);

    // then
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  @DisplayName("계정 삭제 성공 테스트")
  void deleteUser() {
    // given
    User newUser = User.builder().username(SIGNUP_REQUEST_DTO.getUsername()).password(SIGNUP_REQUEST_DTO.getPassword())
        .email(SIGNUP_REQUEST_DTO.getEmail())
        .build();

    given(userRepository.findByUsername(newUser.getUsername()))
        .willReturn(Optional.of(newUser));

    // when
    userService.deleteUser(newUser.getId(), newUser.getUsername());

    // then
    verify(userRepository, times(1)).deleteById(newUser.getId());
  }

  @Test
  @DisplayName("회원정보 조회 성공 테스트")
  void showProfile() {
    // 데이터베이스에 저장되어 있는 사용자의 primary key
    Long userId = 1L;

    // 데이터베이스에 저장되어 있는 사용자 역할
    User user = new User("testUser",
        "1234",
        new Profile("banana", "before"));

    given(userRepository.findById(userId)).willReturn(Optional.of(user));

    // when
    ProfileResponseDto responseDto = userService.showProfile(userId);

    // then
    assertThat(responseDto.getNickname()).isEqualTo(user.getProfile().getNickName());
    assertThat(responseDto.getImg_url()).isEqualTo(user.getProfile().getImg_url());
  }

  @Test
  @DisplayName("회원정보 조회 실패 테스트: 회원 정보가 없는 경우")
  void failsShowProfile() {
    // 데이터베이스에 저장되어 있는 사용자의 primary key
    Long userId = 1L;

    given(userRepository.findById(userId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      userService.showProfile(userId); // when
    }).isInstanceOf(IllegalArgumentException.class); // then
  }

  @Test
  @DisplayName("회원정보 업데이트 성공 테스트")
  void updateProfile() {
    // 데이터베이스에 저장되어 있는 사용자의 primary key
    Long userId = 1L;

    // 데이터베이스에 저장되어 있는 사용자 역할
    User user = new User("testUser",
        "1234",
        new Profile("banana", "before"));

    // 업데이트할 프로필 정보
    ProfileRequestDto requestDto = ProfileRequestDto.builder()
        .nickname("apple")
        .img_url("after")
        .build();

    given(userRepository.findById(userId)).willReturn(Optional.of(user));

    // when
    ProfileResponseDto updatedProfile = userService.updateProfile(userId, requestDto);

    // then
    assertThat(updatedProfile.getNickname()).isEqualTo(requestDto.getNickname());
    assertThat(updatedProfile.getImg_url()).isEqualTo(requestDto.getImg_url());
  }

  @Test
  @DisplayName("회원정보 업데이트 실패 테스트: 회원 정보가 없는 경우")
  void failsUpdateProfile() {
    // 데이터베이스에 저장되어 있는 사용자의 primary key
    Long userId = 1L;

    // 업데이트할 프로필 정보
    ProfileRequestDto requestDto = ProfileRequestDto.builder()
        .nickname("apple")
        .img_url("after")
        .build();

    given(userRepository.findById(userId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> {
      // when
      userService.updateProfile(userId, requestDto);
    }).isInstanceOf(IllegalArgumentException.class); // then
  }
}
