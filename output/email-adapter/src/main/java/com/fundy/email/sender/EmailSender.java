package com.fundy.email.sender;

import jakarta.mail.internet.MimeMessage;

public interface EmailSender {
    void send(MimeMessage mimeMessage);
}
