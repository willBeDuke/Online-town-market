package com.example.townmarket.common.security;

import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다."));
//    UserInformation userInformation = userRepository.getUserInfoByUsername(username)
//        .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다."));
//    User user = User.builder()
//        .id(userInformation.getUserId())
//        .username(userInformation.getUsername())
//        .password(userInformation.getPassword())
//        .role(userInformation.getRoleEnum())
//        .build();
    return new UserDetailsImpl(user);

  }
}
