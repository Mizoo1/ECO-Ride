package com.ECO.login_System.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
/**
 * Konfigurationsklasse für die E-Mail-Versandfunktionen.
 */
@Configuration
public class MailConfig
{
    /**
     * Erstellt und konfiguriert einen JavaMailSender, der für den E-Mail-Versand verwendet wird.
     *
     * @return Der konfigurierte JavaMailSender.
     */
    @Bean
    public JavaMailSender javaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("eco.ride.germany@gmail.com");
        mailSender.setPassword("srpyyfidibekflju");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }

}
