package com.example.townmarket.common.oauth.controller;

import com.example.townmarket.common.domain.user.service.UserService;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.enums.ResponseMessages;
import com.example.townmarket.common.enums.RoleEnum;
import jakarta.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserControllerAuth {

  private final UserService userService;


  @GetMapping("/")
  public String mainPage(Model model) {
    return "index";
  }

  @GetMapping("/users/login2")
  public String loginForm(Model model) {
//      User user = userDetails.getUser();
//      model.addAttribute("user", user.getUsername());
    return "login";
  }

  //OAuth로 로그인 시 비밀번호 입력 창으로
  @GetMapping("/users/oauth/password/{email}/{username}")
  public String oauth(@PathVariable("email") String email,
      @PathVariable("username") String username, Model model) {
    System.out.println("email = " + email);
    System.out.println("username = " + username);
    model.addAttribute("email", email);
    model.addAttribute("username", username);
    return "oauthPassword";
  }

  @PostMapping("/users/login-username")
  @ResponseBody
  @CrossOrigin(origins = "http://localhost:5500")
  public ResponseEntity<StatusResponse> loginOauth(@RequestParam String username, @RequestParam RoleEnum role, HttpServletResponse response){
    userService.loginOAuth2(username, role, response);
    return ResponseEntity.ok().body(StatusResponse.valueOf(ResponseMessages.SUCCESS));
  }
//  @PostMapping("/login/oauth2/code/google")
//  public ResponseEntity<State>

}
