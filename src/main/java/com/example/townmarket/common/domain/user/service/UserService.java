package com.example.townmarket.common.domain.user.service;

import com.example.townmarket.common.domain.user.dto.DuplicateCheckRequestDto;
import com.example.townmarket.common.domain.user.dto.DuplicateCheckResponseDto;
import com.example.townmarket.common.domain.user.dto.LoginRequestDto;
import com.example.townmarket.common.domain.user.dto.PasswordUpdateRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileRequestDto;
import com.example.townmarket.common.domain.user.dto.ProfileResponseDto;
import com.example.townmarket.common.domain.user.dto.SignupRequestDto;
import com.example.townmarket.common.domain.user.dto.UserInfoResponseDto;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.enums.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

  void signup(SignupRequestDto request);

  void login(HttpServletResponse response, LoginRequestDto request);

  void updateUser(String username, PasswordUpdateRequestDto updateDto);

//  void updateRegion(String username, RegionUpdateRequestDto updateDto);

  void deleteUser(Long userId, String username);

  ProfileResponseDto updateProfile(Long profileId, ProfileRequestDto request);

  ProfileResponseDto showProfile(Long profileId);

  List<User> findAllUser();

  UserInfoResponseDto getMyInfo(User user);

  Page<User> pagingUsers(Pageable pageable);

  User findUserById(Long userId);

  User findByUsername(String username);

  boolean existsByEmail(String email);

  ProfileResponseDto getMyProfile(String username);

  DuplicateCheckResponseDto duplicateCheck(DuplicateCheckRequestDto duplicateCheckRequestDto);

  void logout(HttpServletRequest request, HttpServletResponse response);

  void loginOAuth2(String username, RoleEnum role, HttpServletResponse response);

  double getUserAverageGrade(User reviewee);
}


