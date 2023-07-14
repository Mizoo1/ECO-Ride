package com.ECO.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class ContactService
{
    private final JavaMailSender mailSender;
    public ContactService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }
    public void sendEmailContatWithoutLogin(String name,
                                            String vorname,
                                            String adresse,
                                            String telefon,
                                            String mobil,
                                            String email,
                                            String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("eco.ride.germany@gmail.com");
        mailMessage.setSubject("Kontaktformular - Neue Nachricht");
        mailMessage.setText(
                        "Name: " + name + "\n" +
                        "Vorname: " + vorname + "\n" +
                        "Adresse: " + (adresse != null ? adresse : "") + "\n" +
                        "Telefon: " + (telefon != null ? telefon : "") + "\n" +
                        "Mobil: " + (mobil != null ? mobil : "") + "\n" +
                        "E-Mail: " + (email != null ? email : "") + "\n\n" +
                        "Nachricht:\n" + message);

        mailSender.send(mailMessage);
    }
    public void sendEmailContact(Long id ,
                                 String name,
                                 String vorname,
                                 String adresse,
                                 String telefon,
                                 String mobil,
                                 String email,
                                 String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("eco.ride.germany@gmail.com");
        mailMessage.setSubject("Kontaktformular - Neue Nachricht");
        mailMessage.setText("ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Vorname: " + vorname + "\n" +
                "Adresse: " + (adresse != null ? adresse : "") + "\n" +
                "Telefon: " + (telefon != null ? telefon : "") + "\n" +
                "Mobil: " + (mobil != null ? mobil : "") + "\n" +
                "E-Mail: " + (email != null ? email : "") + "\n\n" +
                "Nachricht:\n" + message);
        mailSender.send(mailMessage);
    }
}
