package com.example.townmarket.common.oauth;

import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import com.example.townmarket.common.enums.RoleEnum;
import com.example.townmarket.common.oauth.dto.OAuthDto;
import com.example.townmarket.common.security.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuthDto oAuthDto = OAuthDto.ofGoogle(registrationId, oAuth2User.getAttributes());
    String userName =
        oAuthDto.getName().toLowerCase() + UUID.randomUUID().toString().substring(0, 3);
    String email = oAuthDto.getEmail();
    String password = (registrationId.substring(0, 4) + UUID.randomUUID().toString()
        .substring(0, 4));
    User user = userRepository.findByEmail(email)
        .orElseGet(() -> createUser(email, userName, password, RoleEnum.MEMBER));
    return new UserDetailsImpl(user, oAuth2User.getAttributes());
  }

  private User createUser(String email, String userName, String password, RoleEnum role) {
    User user = userRepository.save(
        User.builder().password(password).email(email).username(userName).role(role)
            .build());
    return user;
  }

}


