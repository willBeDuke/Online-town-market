package com.example.townmarket.common.domain.user.repository;

import com.example.townmarket.common.domain.user.dto.ProfileResponseDto;
import com.example.townmarket.common.domain.user.dto.UserInfoResponseDto;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.UserInformation;
import java.util.Optional;

public interface UserRepositoryQuery {

  boolean existByNickname(String nickname);

  Optional<UserInformation> getUserInfoByUsername(String username);

  User getMyInfoAndAddress(Long userId);
  ProfileResponseDto getProfileByUsername(String username);

}
