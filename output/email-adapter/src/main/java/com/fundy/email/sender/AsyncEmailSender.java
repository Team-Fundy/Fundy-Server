package com.fundy.email.sender;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsyncEmailSender implements EmailSender {
    private final JavaMailSender javaMailSender;

    @Async("ThreadPoolTaskExecutor")
    @Override
    public void send(MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }
}
