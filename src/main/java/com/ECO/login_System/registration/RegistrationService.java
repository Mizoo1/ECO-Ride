package com.ECO.login_System.registration;



import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserRole;
import com.ECO.login_System.appuser.AppUserService;
import com.ECO.login_System.email.EmailSender;
import com.ECO.login_System.registration.token.ConfirmationToken;
import com.ECO.login_System.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = appUserService.signUpUser(
                new AppUser(
                        request.getUserName(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getTitel(),
                        request.getAdresse(),
                        request.getPlz(),
                        request.getStadt(),

                        request.getTelefonnummer(),
                        request.getGeburtsdatum(),
                        request.getGeburtsort(),
                        request.getFuehrerscheinnummer(),
                        request.getErteilungsdatum(),
                        request.getAblaufdatum(),
                        request.getAusstellungsort(),
                        request.getPersonalausweisnummer(),
                        request.getReisepassnummer(),
                        request.getTarif(),
                        AppUserRole.USER,
                        request.getPayMethod()

                )
        );

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(
                        request.getUserName(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getTitel(),
                        request.getAdresse(),
                        request.getPlz(),
                        request.getStadt(),
                        request.getTelefonnummer(),
                        request.getGeburtsdatum(),
                        request.getGeburtsort(),
                        request.getFuehrerscheinnummer(),
                        request.getErteilungsdatum(),
                        request.getAblaufdatum(),
                        request.getAusstellungsort(),
                        request.getPersonalausweisnummer(),
                        request.getReisepassnummer(),
                        request.getTarif()
                        , link, request.getPayMethod()));

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

    private String buildEmail(String userName, String name, String LastName, String Email, String Password, String Titel, String Adresse, String Plz, String Stadt, String Telefonnummer, String Geburtsdatum, String Geburtsort, String Fuehrerscheinnummer, String Erteilungsdatum, String Ablaufdatum, String Ausstellungsort, String Personalausweisnummer, String Reisepassnummer, String Tarif, String link, String payMethod) {
        // HTML-Datei einlesen
        String htmlTemplate = ""; // HTML-Code als String
        try {
            File file = new File("D:\\ECO-Ride\\src\\main\\resources\\templates\\email_template.html");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                htmlTemplate += scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        htmlTemplate = htmlTemplate.replace("{{name}}", name);
        htmlTemplate = htmlTemplate.replace("{{link}}", link);
        htmlTemplate = htmlTemplate.replace("{{Email}}", Email);
        htmlTemplate = htmlTemplate.replace("{{Adresse}}", Adresse);
        htmlTemplate = htmlTemplate.replace("{{Plz}}", Plz);
        htmlTemplate = htmlTemplate.replace("{{Stadt}}", Stadt);
        htmlTemplate = htmlTemplate.replace("{{Telefonnummer}}", Telefonnummer);
        htmlTemplate = htmlTemplate.replace("{{Geburtsdatum}}", Geburtsdatum);
        htmlTemplate = htmlTemplate.replace("{{Geburtsort}}", Geburtsort);
        htmlTemplate = htmlTemplate.replace("{{Fuehrerscheinnummer}}", Fuehrerscheinnummer);
        htmlTemplate = htmlTemplate.replace("{{Erteilungsdatum}}", Erteilungsdatum);
        htmlTemplate = htmlTemplate.replace("{{Ablaufdatum}}", Ablaufdatum);
        htmlTemplate = htmlTemplate.replace("{{Ausstellungsort}}", Ausstellungsort);
        htmlTemplate = htmlTemplate.replace("{{Personalausweisnummer}}", Personalausweisnummer);
        htmlTemplate = htmlTemplate.replace("{{Reisepassnummer}}", Reisepassnummer);
        htmlTemplate = htmlTemplate.replace("{{payMethod}}", payMethod);
        htmlTemplate = htmlTemplate.replace("{{Tarif}}", Tarif);
        htmlTemplate = htmlTemplate.replace("{{Titel}}", Titel);
        htmlTemplate = htmlTemplate.replace("{{LastName}}", LastName);
        htmlTemplate = htmlTemplate.replace("{{userName}}", userName);

        return htmlTemplate;
    }

}