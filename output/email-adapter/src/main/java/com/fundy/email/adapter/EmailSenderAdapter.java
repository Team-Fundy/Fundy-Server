package com.fundy.email.adapter;

import com.fundy.application.email.out.SendVerifyCodeEmailPort;
import com.fundy.email.message.VerifyMessageGenerator;
import com.fundy.email.sender.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderAdapter implements SendVerifyCodeEmailPort {
    private final EmailSender emailSender;
    private final VerifyMessageGenerator verifyMessageGenerator;

    @Override
    public void sendVerifyCode(String email, String code) {
        emailSender.send(verifyMessageGenerator.generateVerifyMessage(email, code));
    }
}