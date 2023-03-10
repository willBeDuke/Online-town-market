package com.example.townmarket.common.domain.user.service;

import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.address.service.AddressServiceImpl;
import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.review.service.UserGradeServiceImpl;
import com.example.townmarket.common.domain.user.dto.DuplicateCheckRequestDto;
import com.example.townmarket.common.domain.user.dto.DuplicateCheckResponseDto;
import com.example.townmarket.common.domain.user.dto.LoginRequestDto;
import com.example.townmarket.common.domain.user.dto.PasswordUpdateRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileResponseDto;
import com.example.townmarket.common.domain.user.dto.SignupRequestDto;
import com.example.townmarket.common.domain.user.dto.UserInfoResponseDto;
import com.example.townmarket.common.domain.user.entity.Profile;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import com.example.townmarket.common.enums.RoleEnum;
import com.example.townmarket.common.jwtUtil.JwtUtil;
import com.example.townmarket.common.redis.entity.Tokens;
import com.example.townmarket.common.redis.service.RefreshService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;
  private final RefreshService refreshService;
  private final UserGradeServiceImpl userGradeService;
  private final AddressServiceImpl addressService;

  @Override
  @Transactional
  public void signup(SignupRequestDto request) {
    String username = request.getUsername();
    String email = request.getEmail();
    String password = passwordEncoder.encode(request.getPassword());

    if (request.getImg_url() != null) {
      Profile profile = Profile.builder().nickName(request.getNickname()).img_url(
          request.getImg_url()).build();
      User user = User.builder()
          .username(username)
          .password(password)
          .email(email)
          .role(RoleEnum.MEMBER)
          .profile(profile)
          .build();

      userRepository.save(user);
      Address address = new Address(request.getAddress1(), request.getAddress2(),
          request.getAddress3(), user);
      addressService.addressSave(address);
    }
    Profile profile = new Profile(request.getNickname());
    User user = User.builder()
        .username(username)
        .password(password)
        .email(email)
        .role(RoleEnum.MEMBER)
        .profile(profile)
        .build();

    userRepository.save(user);
    Address address = new Address(request.getAddress1(), request.getAddress2(),
        request.getAddress3(), user);
    addressService.addressSave(address);
  }

  @Override
  @Transactional
  public void login(HttpServletResponse response, LoginRequestDto request) {
    String username = request.getUsername();
    String password = request.getPassword();

    // 사용자 확인
    User user = this.findByUsername(username);

    // 비밀번호 확인
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("입력한 정보가 틀립니다.");
    }
    // token 발급
    String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
    String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), user.getRole());
    response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);

  }

  @Override
  @Transactional
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    String accessToken = jwtUtil.resolveAccessToken(request);
    Claims userInfoFromToken = jwtUtil.getUserInfoFromToken(accessToken);
    String refreshToken = jwtUtil.resolveRefreshToken(request);
    String username = userInfoFromToken.getSubject();
    Tokens tokens = Tokens.builder().token(refreshToken).id(username).build();

    // token blacklist 저장
    refreshService.saveBlackList(tokens);
    response.setHeader(JwtUtil.AUTHORIZATION_HEADER, null);
    response.setHeader(JwtUtil.REFRESH_HEADER, null);
  }

  @Override
  public void loginOAuth2(String username, RoleEnum role, HttpServletResponse response) {
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createAccessToken(username, role));
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createRefreshToken(username, role));
  }

  @Override
  @Transactional
  public double getUserAverageGrade(User reviewee) {
    Set<UserGrade> userGrades = userGradeService.findAllByReviewee(reviewee);
    return reviewee.getUserAverageGrade();
  }


  @Override
  @Transactional
  public void updateUser(String username, PasswordUpdateRequestDto updateDto) {
    User user = this.findByUsername(username);
    if (!user.checkAuthorization(user)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    }
    String password = passwordEncoder.encode(updateDto.getPassword());
    user.updatePassword(password);
    this.userRepository.save(user);
  }

//  @Deprecated
//  @Override
//  @Transactional
//  public void updateRegion(String username, RegionUpdateRequestDto updateRequestDto) {
//    User user = this.findByUsername(username);
//
//    if (!user.checkAuthorization(user)) {
//      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
//    }
////    user.updateRegion(updateRequestDto);
//    this.userRepository.save(user);
//  }

  @Override
  @Transactional
  public void deleteUser(Long userId, String username) {
    User user = this.findByUsername(username);

    if (!user.checkAuthorization(user)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
    }
    userRepository.deleteById(userId);
  }

  @Transactional
  @Override
  public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto request) {
    Profile profileSaved = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("회원 없음")).getProfile();
    profileSaved.update(request);
    return new ProfileResponseDto(profileSaved);
  }

  @Transactional
  @Override
  public ProfileResponseDto showProfile(Long userId) {
    Profile profile = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("회원 없음")).getProfile();
    return new ProfileResponseDto(profile);
  }


  @Override
  public List<User> findAllUser() {
    return userRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public UserInfoResponseDto getMyInfo(User user) {
    return new UserInfoResponseDto(userRepository.getMyInfoAndAddress(user.getId()));
  }

  @Override
  public User findUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(
        () -> new RuntimeException("회원을 찾을 수 없습니다.")
    );
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다.")
    );
  }

  @Override
  public Page<User> pagingUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }


  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public ProfileResponseDto getMyProfile(String username) {
    return userRepository.getProfileByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public DuplicateCheckResponseDto duplicateCheck(
      DuplicateCheckRequestDto duplicateCheckRequestDto) {
    switch (duplicateCheckRequestDto.getDuplicateField()) {
      case ("username") -> {
        return new DuplicateCheckResponseDto(
            userRepository.existsByUsername(duplicateCheckRequestDto.getContent()));
      }
      case ("email") -> {
        return new DuplicateCheckResponseDto(
            userRepository.existsByEmail(duplicateCheckRequestDto.getContent()));
      }
      case ("nickname") -> {
        return new DuplicateCheckResponseDto(
            userRepository.existByNickname(duplicateCheckRequestDto.getContent()));
      }
    }
    throw new IllegalArgumentException("잘못된 입력입니다");
  }

}

