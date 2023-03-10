package com.example.townmarket.common.security;

import com.example.townmarket.admin.entity.Admin;
import com.example.townmarket.common.enums.RoleEnum;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminDetailsImpl implements UserDetails {

  private final Admin admin;

  public AdminDetailsImpl(Admin admin) {
    this.admin = admin;
  }

  public Admin getAdmin() {
    return admin;
  }

  public Long getAdminId() {
    return admin.getId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    RoleEnum role = admin.getRole();
    String authority = role.getAuthority();

    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(simpleGrantedAuthority);

    return authorities;
  }

  @Override
  public String getPassword() {
    return this.admin.getPassword();
  }

  @Override
  public String getUsername() {
    return this.admin.getUsername();
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
}

