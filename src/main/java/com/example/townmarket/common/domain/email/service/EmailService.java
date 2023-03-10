package com.example.townmarket.common.domain.email.service;

public interface EmailService {

  void sendSimpleMessage(String to) throws Exception;

  boolean verifyCode(String email, String code);
}
