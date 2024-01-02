package com.fundy.email.message;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MessageGenerator implements VerifyMessageGenerator{
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private MimeMessage generateMimeMeessage(String subject, String content, String email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setSubject(subject);
            message.setRecipients(Message.RecipientType.TO, email);
            message.setText(content,"utf-8", "html");
            return message;
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Email Message Create Fail");
        }
    }
    @Override
    public MimeMessage generateVerifyMessage(String email, String code) {
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("code", code);
        String content = templateEngine.process("sendCode",context);

        return generateMimeMeessage("Fundy 이메일 인증 안내", content, email);
    }
}