package com.fundy.email.message;

import jakarta.mail.internet.MimeMessage;

public interface VerifyMessageGenerator {
    MimeMessage generateVerifyMessage(String email, String code);
}
