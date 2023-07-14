package com.ECO.login_System.registration;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.Reservation;
import com.ECO.login_System.appuser.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class ReservationConfirmationEmailService
{
    private final JavaMailSender mailSender;

    @Value("${email.reservation.confirmation.subject}")
    private String confirmationEmailSubject;

    @Value("${email.reservation.cancellation.subject}")
    private String cancellationEmailSubject;

    @Value("${email.reservation.missed.subject}")
    private String missedEmailSubject;

    @Value("${email.reservation.completed.subject}")
    private String completedEmailSubject;
    @Autowired
    public ReservationConfirmationEmailService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }
    /**
     * Sendet eine Reservierungsbestätigungs-E-Mail an den Empfänger.
     *
     * @param to          Die E-Mail-Adresse des Empfängers.
     * @param status      Der Status der Reservierung.
     * @param appUser     Der AppUser, der die Reservierung vorgenommen hat.
     * @param reservation Die Reservierung.
     */
    public void sendReservationConfirmationEmail(String to,
                                                 ReservationStatus status,
                                                 AppUser appUser,
                                                 Reservation reservation
    ) {
        try
        {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            String emailSubject = getEmailSubjectByStatus(status);
            helper.setSubject(emailSubject);
            helper.setFrom("hello@eco-ride.com");
            String htmlTemplate = readHtmlTemplateByStatus(status);
            htmlTemplate = htmlTemplate.replace("{reservationDueDatum}", replaceNullWithEmptyString(reservation.getReservierungsDatum().toString()));
            htmlTemplate = htmlTemplate.replace("{reservationDueDatum}", replaceNullWithEmptyString(reservation.getReservierungsDatum().toString()));
            htmlTemplate = htmlTemplate.replace("{reservationId}", replaceNullWithEmptyString(reservation.getId().toString()));
            htmlTemplate = htmlTemplate.replace("{reservationDatum}", replaceNullWithEmptyString(reservation.getDatum()));
            htmlTemplate = htmlTemplate.replace("{reservationZeit}", replaceNullWithEmptyString(reservation.getZeit()));
            htmlTemplate = htmlTemplate.replace("{reservationAbholort}", replaceNullWithEmptyString(reservation.getAbholort()));
            htmlTemplate = htmlTemplate.replace("{reservationOrt}", replaceNullWithEmptyString(reservation.getOrt()));
            htmlTemplate = htmlTemplate.replace("{reservationFahrzeug}", replaceNullWithEmptyString(reservation.getFahrzeug()));
            htmlTemplate = htmlTemplate.replace("{reservationModelPkw}", replaceNullWithEmptyString(reservation.getModelPkw()));
            htmlTemplate = htmlTemplate.replace("{reservationMarke}", replaceNullWithEmptyString(reservation.getMarke()));
            htmlTemplate = htmlTemplate.replace("{reservationAnzahlDerTuren}", replaceNullWithEmptyString(reservation.getAnzahlDerTuren()));
            htmlTemplate = htmlTemplate.replace("{reservationSprit}", replaceNullWithEmptyString(reservation.getSprit()));
            htmlTemplate = htmlTemplate.replace("{reservationModelLkw}", replaceNullWithEmptyString(reservation.getModelLkw()));
            htmlTemplate = htmlTemplate.replace("{reservationLkwSize}", replaceNullWithEmptyString(reservation.getLkwSize()));
            htmlTemplate = htmlTemplate.replace("{reservationMotoradSize}", replaceNullWithEmptyString(reservation.getMotoradSize()));
            htmlTemplate = htmlTemplate.replace("{reservationModelMotorad}", replaceNullWithEmptyString(reservation.getModelMotorad()));
            htmlTemplate = htmlTemplate.replace("{reservationMotoradMarke}", replaceNullWithEmptyString(reservation.getMotoradMarke()));
            htmlTemplate = htmlTemplate.replace("{reservationAnhaengerSize}", replaceNullWithEmptyString(reservation.getAnhaengerSize()));
            String userName = appUser.getUserName();
            String email = appUser.getEmail();
            htmlTemplate = htmlTemplate.replace("{userName}", replaceNullWithEmptyString(userName));
            htmlTemplate = htmlTemplate.replace("{email}", replaceNullWithEmptyString(email));
            helper.setText(htmlTemplate, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }
    private String getEmailSubjectByStatus(ReservationStatus status)
    {
        switch (status)
        {
            case RESERVED:
                return confirmationEmailSubject;
            case CANCELLED:
                return cancellationEmailSubject;
            case MISSED:
                return missedEmailSubject;
            case COMPLETED:
                return completedEmailSubject;
            default:
                return "";
        }
    }
    private String readHtmlTemplateByStatus(ReservationStatus status)
    {
        try
        {
            String templateFileName = getTemplateFileNameByStatus(status);
            Resource resource = new ClassPathResource("templates/" + templateFileName);
            InputStream inputStream = resource.getInputStream();
            byte[] htmlBytes = StreamUtils.copyToByteArray(inputStream);
            return new String(htmlBytes, StandardCharsets.UTF_8);
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    private String replaceNullWithEmptyString(String value)
    {
        return value != null ? value : "";
    }
    private String getTemplateFileNameByStatus(ReservationStatus status)
    {
        switch (status)
        {
            case RESERVED:
                return "reservation_confirmation.html";
            case CANCELLED:
                return "reservation_cancellation.html";
            case MISSED:
                return "reservation_missed.html";
            case COMPLETED:
                return "reservation_completed.html";
            default:
                return "";
        }
    }
}
