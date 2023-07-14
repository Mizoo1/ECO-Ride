package com.ECO.login_System.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
/**
 * Service-Klasse für den Versand von E-Mails.
 * Implementiert das `EmailSender`-Interface.
 */
@Service
@AllArgsConstructor
public class EmailService implements EmailSender
{
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    /**
     * Sendet eine E-Mail mit dem angegebenen Inhalt an die angegebene E-Mail-Adresse.
     *
     * @param to    Die Ziel-E-Mail-Adresse.
     * @param email Der Inhalt der E-Mail.
     */
    @Override
    @Async
    public void send(String to, String email)
    {
        try
        {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to); //"eco.ride.germany@gmail.com"
            helper.setSubject("Confirm your email");
            helper.setFrom("hello@eco-ride.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e)
        {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
    /**
     * Sendet eine E-Mail mit dem angegebenen Inhalt, Betreff und an die angegebene E-Mail-Adresse.
     *
     * @param to      Die Ziel-E-Mail-Adresse.
     * @param subject Der Betreff der E-Mail.
     * @param email   Der Inhalt der E-Mail.
     */
    @Override
    @Async
    public void send(String to, String subject, String email)
    {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("hello@eco-ride.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e)
        {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}