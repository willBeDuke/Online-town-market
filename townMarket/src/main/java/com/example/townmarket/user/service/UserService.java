package com.example.townmarket.user.service;

import com.example.townmarket.user.dto.LoginRequestDto;
import com.example.townmarket.user.dto.PasswordUpdateRequestDto;
import com.example.townmarket.user.dto.ProfileRequestDto;
import com.example.townmarket.user.dto.ProfileResponseDto;
import com.example.townmarket.user.dto.RegionUpdateRequestDto;
import com.example.townmarket.user.dto.SignupRequestDto;
import com.example.townmarket.user.entity.Profile;
import com.example.townmarket.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {

  String signup(SignupRequestDto request);

  String login(HttpServletResponse response, LoginRequestDto request);

  void logout(User user);

  void updateUser(String username, PasswordUpdateRequestDto updateDto);

  void updateRegion(String username, RegionUpdateRequestDto updateDto);

  void deleteUser(Long userId, String username);

  Profile updateProfile(Long profileId, ProfileRequestDto request);

  ProfileResponseDto showProfile(Long profileId);

  List<User> findAllUser();

  void setUserGrade(User reviewee, int grade, int count);

  User findUserById(Long userId);

  void updateUserGrade(User reviewee, int grade);

  boolean existsByEmail(String email);

  void loginOAuth2(HttpServletResponse response, OAuth2User oAuth2User);
}


