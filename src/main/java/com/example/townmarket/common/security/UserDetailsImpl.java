package com.example.townmarket.common.security;

import com.example.townmarket.common.enums.RoleEnum;
import com.example.townmarket.common.domain.user.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserDetailsImpl implements UserDetails, OAuth2User {

  private final User user;
  private Map<String, Object> attributes;


  public UserDetailsImpl(User user) {
    this.user = user;
  }

  //OAuth2User : OAuth2 로그인 시 사용
  public UserDetailsImpl(User user, Map<String, Object> attributes) {
    //PrincipalOauth2UserService 참고
    this.user = user;
    this.attributes = attributes;
  }

  public User getUser() {
    return user;
  }

  public Long getUserId() {
    return user.getId();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    RoleEnum role = user.getRole();
    String authority = role.getAuthority();

    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(simpleGrantedAuthority);

    return authorities;
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  @Override
  public String getName() {
    return user.getUsername();
  }
}
