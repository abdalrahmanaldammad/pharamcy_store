package com.example.pharmacystore.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

  @NonNull private JavaMailSender mailSender;
  @NonNull private TemplateEngine templateEngine;

  @Value("${spring.mail.username}")
  private String fromEmail;

  public String generateVerificationCode() {
    Random rand = new Random();
    int code = 100000 + rand.nextInt(900000);
    return String.valueOf(code);
  }

  public void sendVerificationEmail(String to, String verificationCode) throws MessagingException {
    Context context = new Context();
    context.setVariable("verificationCode", verificationCode);

    String content = templateEngine.process("email-verification", context);

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom(fromEmail);
    helper.setTo(to);
    helper.setSubject("your verification code");
    helper.setText(content, true);

    mailSender.send(mimeMessage);
  }
}
