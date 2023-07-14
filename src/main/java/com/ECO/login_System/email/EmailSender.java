package com.ECO.login_System.email;

import org.springframework.scheduling.annotation.Async;

public interface EmailSender {
    void send(String to, String email);
    @Async
    void send(String to, String subject, String email);
}