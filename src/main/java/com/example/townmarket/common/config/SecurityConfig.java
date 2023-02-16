package com.example.townmarket.common.config;

import com.example.townmarket.common.jwtUtil.JwtUtil;
import com.example.townmarket.common.oauth.OAuth2SuccessHandler;
import com.example.townmarket.common.oauth.OAuth2UserServiceImpl;
import com.example.townmarket.common.security.AdminDetailsServiceImpl;
import com.example.townmarket.common.security.CustomAccessDeniedHandler;
import com.example.townmarket.common.security.CustomAuthenticationEntryPoint;
import com.example.townmarket.common.security.JwtAuthFilter;
import com.example.townmarket.common.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

  private final String[] permitAllArray = {
      "/",
      "/users/**",
      "/users/login/",
      "/users/login2/",
      "/users/signup/",
      "/users/update/**",
      "/users/profile/**",
      "/css/**",
      "/js/**",
      "/images/**",
      "/login/oauth2/code/google",
      "/users/oauth/password/**",
      "profile"
  };
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  private final AdminDetailsServiceImpl adminDetailsService;

  private final OAuth2UserServiceImpl oAuth2UserService;

  private final OAuth2SuccessHandler oAuth2SuccessHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(PathRequest.toH2Console())
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        .requestMatchers("/api/**")
        .requestMatchers("/docs/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:8080", "http://127.0.0.1:5500") // 허용할 출처
        .allowedMethods("GET", "POST", "PATCH", "OPTIONS", "DELETE") // 허용할 HTTP method
        .allowCredentials(true) // 쿠키 인증 요청 허용
        .exposedHeaders("Authorization")
        .maxAge(3000);// 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers(permitAllArray).permitAll()
        .requestMatchers("/admin/users").hasAnyRole("TOP_MANAGER", "MIDDLE_MANAGER")
        .anyRequest().authenticated()
        .and().addFilterBefore(new JwtAuthFilter(jwtUtil, userDetailsService, adminDetailsService),
            UsernamePasswordAuthenticationFilter.class);

//    http.formLogin().loginPage("/api/user/login-page").permitAll();
    http.oauth2Login()//OAuth 로그인 기능에 대한 여러 설정의 진입
        .userInfoEndpoint()// 로그인 성공 이후 사용자 정보를 가져올 때의 설정
        .userService(oAuth2UserService); //소셜 로그인 성공 후 후속 조치를 진행할 서비스의 구현체 등록
    http.oauth2Login().successHandler(oAuth2SuccessHandler);

    //401 인증과정 실패시 에러처리
    http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
    //403
    http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

    return http.build();
  }

}
