package com.example.townmarket.common.domain.user.controller;

import static com.example.townmarket.common.domain.user.controller.UserController.USER_API_URI;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_CREATED;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_DELETE;
import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_OK;

import com.example.townmarket.common.domain.user.dto.DuplicateCheckRequestDto;
import com.example.townmarket.common.domain.user.dto.DuplicateCheckResponseDto;
import com.example.townmarket.common.domain.user.dto.LoginRequestDto;
import com.example.townmarket.common.domain.user.dto.PasswordUpdateRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileResponseDto;
import com.example.townmarket.common.domain.user.dto.SignupRequestDto;
import com.example.townmarket.common.domain.user.dto.UserInfoResponseDto;
import com.example.townmarket.common.domain.user.service.UserService;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_API_URI)
public class UserController {

  public static final String USER_API_URI = "/users";
  private final UserService userService;
  private final SetHttpHeaders httpHeaders;

  @PostMapping("/duplicate")
  public ResponseEntity<DuplicateCheckResponseDto> duplicateCheck(
      @RequestBody DuplicateCheckRequestDto duplicateCheckRequestDto) {
    return ResponseEntity.ok().headers(httpHeaders.setHeaderTypeJson())
        .body(userService.duplicateCheck(duplicateCheckRequestDto));
  }

  @PostMapping("/signup")
  public ResponseEntity<StatusResponse> signup(
      @Validated @RequestBody SignupRequestDto signupRequestDto) {
    userService.signup(signupRequestDto);
    return RESPONSE_CREATED;
  }

  @PostMapping("/login")
  public ResponseEntity<StatusResponse> login(
      @RequestBody LoginRequestDto loginRequestDto,
      HttpServletResponse response) {
    userService.login(response, loginRequestDto);
    return RESPONSE_OK;

  }

  @PostMapping("/logout")
  public ResponseEntity<StatusResponse> logout(HttpServletRequest request,
      HttpServletResponse response) {
    userService.logout(request, response);
    return RESPONSE_OK;
  }

  @PutMapping("/update/pw")
  public ResponseEntity<StatusResponse> updateUser(
      @Validated @RequestBody PasswordUpdateRequestDto updateRequestDto,
      @AuthenticationPrincipal
      UserDetailsImpl userDetails) {
    userService.updateUser(userDetails.getUsername(), updateRequestDto);
    return RESPONSE_OK;
  }

//  @PutMapping("/update/region")
//  public ResponseEntity<StatusResponse> updateRegion(
//      @RequestBody RegionUpdateRequestDto updateRequestDto,
//      @AuthenticationPrincipal
//      UserDetailsImpl userDetails) {
//    userService.updateRegion(userDetails.getUsername(), updateRequestDto);
//    return RESPONSE_OK;
//  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<StatusResponse> deleteUser(@PathVariable Long userId,
      @AuthenticationPrincipal
      UserDetailsImpl userDetails) {
    userService.deleteUser(userId, userDetails.getUsername());
    return RESPONSE_DELETE;
  }


  @PatchMapping("/profile/update")
  public ResponseEntity<StatusResponse> updateProfile(
      @RequestBody ProfileRequestDto request, @AuthenticationPrincipal
  UserDetailsImpl userDetails) {
    userService.updateProfile(userDetails.getUserId(), request);
    return RESPONSE_OK;
  }

  @GetMapping("/profile/{userId}")
  public ResponseEntity<ProfileResponseDto> showProfile(@PathVariable Long userId
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.showProfile(userId));
  }

  @GetMapping("/profile")
  public ResponseEntity<ProfileResponseDto> getMyProfile(@AuthenticationPrincipal
  UserDetailsImpl userDetails) {
    return ResponseEntity.ok().headers(httpHeaders.setHeaderTypeJson())
        .body(userService.getMyProfile(userDetails.getUsername()));
  }

  @GetMapping("/info")
  public ResponseEntity<UserInfoResponseDto> getMyInfo(@AuthenticationPrincipal
  UserDetailsImpl userDetails) {
    return ResponseEntity.ok().headers(httpHeaders.setHeaderTypeJson())
        .body(userService.getMyInfo(userDetails.getUser()));
  }

}
