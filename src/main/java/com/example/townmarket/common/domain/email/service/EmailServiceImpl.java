package com.example.townmarket.common.domain.email.service;

import com.example.townmarket.common.domain.email.entity.EmailVerify;
import com.example.townmarket.common.domain.email.repository.EmailVerifyRepository;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender emailSender;
  public String ePw;

  private final EmailVerifyRepository emailVerifyRepository;


  private MimeMessage createMessage(String to) throws Exception {

    MimeMessage message = emailSender.createMimeMessage();

    message.addRecipients(RecipientType.TO, to);//보내는 대상
    message.setSubject("낙낙상회 회원가입 인증 메일입니다.");//제목

    String msgg = "";
    msgg += "<div style='margin:20px;'>";
    msgg += "<h1>이메일 인증 코드</h1>";
    msgg += "<br>";
    msgg += "<p>내가 필요한 물건을 파는 이웃에게 knock, knock! 낙낙상회에 가입하신 것을 환영합니다.<p>";
    msgg += "<p>아래 인증번호를 8자리를 인증번호 입력창에 입력해주세요.<p>";
    msgg += "<br>";
    msgg += "<div style='background-color: ghostwhite; font-family:verdana; padding-left: 20px'>";
    msgg += "<div style='font-size:180%'>";
    msgg += createKey() + "<div>";
    msgg += "</div>";
    message.setText(msgg, "utf-8", "html");//내용
    message.setFrom(new InternetAddress("5w31892p@gmail.com", "낙낙상회"));//보내는 사람
    System.out.println("보내는 대상 : " + to);
    System.out.println("인증 번호 : " + ePw);
    return message;

  }

  public String createKey() {
    StringBuffer key = new StringBuffer();
    Random rnd = new Random();

    for (int i = 0; i < 8; i++) { // 인증코드 8자리
      int index = rnd.nextInt(3); // 0~2 까지 랜덤

      switch (index) {
        case 0 -> key.append((char) ((int) (rnd.nextInt(26)) + 97));

        //  a~z  (ex. 1+97=98 => (char)98 = 'b')
        case 1 -> key.append((char) ((int) (rnd.nextInt(26)) + 65));

        //  A~Z
        case 2 -> key.append((rnd.nextInt(10)));

        // 0~9
      }
    }
    ePw = key.toString();
    return ePw;
  }

  @Override
  public void sendSimpleMessage(String to) throws Exception {

    MimeMessage message = createMessage(to);
    try {//예외처리
      emailSender.send(message);
    } catch (MailException es) {
      es.printStackTrace();
      throw new IllegalArgumentException();
    }
    emailVerifyRepository.save(EmailVerify.builder().email(to).code(ePw).build());
//    return ePw;
  }

  @Override
  public boolean verifyCode(String email, String code) {
//    boolean result = false;
//    System.out.println("authCode : " + EmailServiceImpl.ePw);
//    System.out.println("myCode : " + code);
//    System.out.println("code match : " + EmailServiceImpl.ePw.equals(code));
//    if (EmailServiceImpl.ePw.equals(code)) {
//      result = true;
//    }
//    return result;
//  }
    Optional<EmailVerify> verify = emailVerifyRepository.findById(email);
    return verify.map(emailVerify -> emailVerify.verifyCheck(code)).orElse(false);
  }
}
