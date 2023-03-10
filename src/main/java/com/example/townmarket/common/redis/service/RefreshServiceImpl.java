package com.example.townmarket.common.redis.service;


import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import com.example.townmarket.common.jwtUtil.JwtUtil;
import com.example.townmarket.common.redis.entity.Tokens;
import com.example.townmarket.common.redis.repository.BlacklistTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

  private final UserRepository userRepository;

  private final BlacklistTokenRepository blacklistTokenRepository;
  private final JwtUtil jwtUtil;

//  private final TokenDtoToByteArrayConverter tokenDtoToByteArrayConverter;


  @Override // 토큰 블랙리스트
  public void saveBlackList(Tokens tokens) {
    blacklistTokenRepository.save(tokens);
  }

  @Override // 토큰 재발급
  public void regenerateAccessToken(HttpServletRequest request, HttpServletResponse response) {

    String token = jwtUtil.resolveRefreshToken(request);
    String username = jwtUtil.getUserInfoFromToken(token).getSubject();
    User user = findByUsername(username);
    Optional<Tokens> tokens = blacklistTokenRepository.findById(username);
    if (tokens.isPresent()) {
      checkValidateToken(response, token, user, tokens);
    } else {
      response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
          jwtUtil.createAccessToken(username, user.getRole()));
    }

  }

  private void checkValidateToken(HttpServletResponse response, String token, User user,
      Optional<Tokens> tokens) {
    if (!tokens.get().checkRefreshToken(token)) {
      response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
          jwtUtil.createAccessToken(user.getUsername(), user.getRole()));
    } else {
      throw new IllegalArgumentException("다시 로그인해주십시오");
    }
  }

  private User findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다"));
  }
}
