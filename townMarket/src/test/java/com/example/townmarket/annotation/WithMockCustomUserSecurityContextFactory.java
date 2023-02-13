package com.example.townmarket.annotation;

import com.example.townmarket.commons.jwtUtil.JwtUtil;
import com.example.townmarket.commons.security.UserDetailsImpl;
import com.example.townmarket.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

  @Override
  public SecurityContext createSecurityContext(WithCustomMockUser annotation) {

    final SecurityContext context = SecurityContextHolder.createEmptyContext();
    // Authentication 은 본인이 인증에 사용하는 클래스를 생성하면 됩니다.
    User user = User.builder().username(annotation.username()).password(annotation.password()).build();
    UserDetailsImpl userDetails = new UserDetailsImpl(user);
    final Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    context.setAuthentication(auth);
    return context;  }
}
