package com.example.townmarket.common.security;

import com.example.townmarket.admin.entity.Admin;
import com.example.townmarket.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {

  private final AdminRepository adminRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Admin admin = adminRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다."));
    return new AdminDetailsImpl(admin);
  }
}
