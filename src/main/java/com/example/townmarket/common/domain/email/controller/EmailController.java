package com.example.townmarket.common.domain.email.controller;


import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_OK;

import com.example.townmarket.common.domain.email.service.EmailService;
import com.example.townmarket.common.domain.user.controller.UserController;
import com.example.townmarket.common.dto.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class EmailController {

  private final EmailService emailService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/mail")
  public ModelAndView emailPage() {
    return new ModelAndView("email");
  }

  @PostMapping("/mail")
  public ResponseEntity<StatusResponse> emailConfirm(@RequestParam String email) throws Exception {
    logger.info("post emailConfirm");
    emailService.sendSimpleMessage(email);
    return RESPONSE_OK;
  }

  @PostMapping("/verify")
  public boolean verifyCode(String email, String code) {
    logger.info("Post verifyCode");
    return emailService.verifyCode(email, code);
  }
}
