package com.ECO.login_System.registration;
/*

import com.ECO.login_System.email.EmailSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PinEmailService {

    private final EmailSender emailSender;

    @Value("${pin.email.subject}")
    private String emailSubject;

    public PinEmailService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendPinEmail(String to) {
        // Generiere eine zufällige 6-stellige PIN
        String pin = RandomStringUtils.randomNumeric(6);

        // Erstelle den E-Mail-Text
        String emailText = "Ihre PIN lautet: " + pin;

        // Sende die E-Mail-Nachricht
        emailSender.send(to, emailSubject, emailText);
    }
}

*/