package com.ECO.login_System.registration;


import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserRepository;
import com.ECO.login_System.appuser.AppUserRole;
import com.ECO.login_System.appuser.AppUserService;
import com.ECO.login_System.email.EmailSender;
import com.ECO.login_System.registration.token.ConfirmationToken;
import com.ECO.login_System.registration.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;


import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

@Service
//@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final AppUserRepository appUserRepository;

    @Autowired
    public RegistrationService(AppUserService appUserService, EmailValidator emailValidator, ConfirmationTokenService confirmationTokenService, EmailSender emailSender, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
        this.appUserRepository = appUserRepository;
    }



    public String registerUser(RegistrationRequest request, HttpServletRequest servletRequest) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        AppUser appUser = new AppUser(
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
                request.getPayMethod(),
                getOperatingSystemFromServletRequest(servletRequest)
        );

        String token = appUserService.signUpUser(appUser);

        String serverName = servletRequest.getServerName();
        int serverPort = servletRequest.getServerPort();
        String link = "http://" + serverName + ":" + serverPort + "/api/v1/registration/confirm?token=" + token;

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
                        request.getTarif(),
                        link,
                        request.getPayMethod()
                )
        );

        return token;
    }

    public String registerAdmin(RegistrationRequest request, HttpServletRequest servletRequest) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        AppUser appUser = new AppUser(

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
                AppUserRole.ADMIN

        );

        String token = appUserService.signUpUser(appUser);

        String serverUrl = servletRequest.getRequestURL().toString();
        String link = serverUrl + "/confirm?token=" + token;

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
                        link
                )
        );
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
    private String buildEmail(String userName, String name, String lastName, String email, String password, String titel, String adresse, String plz, String stadt, String telefonnummer, String geburtsdatum, String geburtsort, String fuehrerscheinnummer, String erteilungsdatum, String ablaufdatum, String ausstellungsort, String personalausweisnummer, String reisepassnummer, String tarif, String link, String payMethod) {
        String htmlTemplate = "";
        try {
            InputStream inputStream = getClass().getResourceAsStream("/templates/email_template.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                htmlTemplate += line;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlTemplate = htmlTemplate.replace("{{name}}", name);
        htmlTemplate = htmlTemplate.replace("{{link}}", link);
        htmlTemplate = htmlTemplate.replace("{{Email}}", email);
        htmlTemplate = htmlTemplate.replace("{{Adresse}}", adresse);
        htmlTemplate = htmlTemplate.replace("{{Plz}}", plz);
        htmlTemplate = htmlTemplate.replace("{{Stadt}}", stadt);
        htmlTemplate = htmlTemplate.replace("{{Telefonnummer}}", telefonnummer);
        htmlTemplate = htmlTemplate.replace("{{Geburtsdatum}}", geburtsdatum);
        htmlTemplate = htmlTemplate.replace("{{Geburtsort}}", geburtsort);
        htmlTemplate = htmlTemplate.replace("{{Fuehrerscheinnummer}}", fuehrerscheinnummer);
        htmlTemplate = htmlTemplate.replace("{{Erteilungsdatum}}", erteilungsdatum);
        htmlTemplate = htmlTemplate.replace("{{Ablaufdatum}}", ablaufdatum);
        htmlTemplate = htmlTemplate.replace("{{Ausstellungsort}}", ausstellungsort);
        htmlTemplate = htmlTemplate.replace("{{Personalausweisnummer}}", personalausweisnummer);
        htmlTemplate = htmlTemplate.replace("{{Reisepassnummer}}", reisepassnummer);
        htmlTemplate = htmlTemplate.replace("{{payMethod}}", payMethod);
        htmlTemplate = htmlTemplate.replace("{{Tarif}}", tarif);
        htmlTemplate = htmlTemplate.replace("{{Titel}}", titel);
        htmlTemplate = htmlTemplate.replace("{{LastName}}", lastName);
        htmlTemplate = htmlTemplate.replace("{{userName}}", userName);

        return htmlTemplate;
    }
    private String buildEmail(String userName, String name, String lastName, String email, String password, String titel, String adresse, String plz, String stadt, String telefonnummer,String geburtsdatum, String link) {
        // HTML-Datei einlesen
        String htmlTemplate = "";
        try {
            InputStream inputStream = getClass().getResourceAsStream("/templates/email_template.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                htmlTemplate += line;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlTemplate = htmlTemplate.replace("{{name}}", name);
        htmlTemplate = htmlTemplate.replace("{{link}}", link);
        htmlTemplate = htmlTemplate.replace("{{Email}}", email);
        htmlTemplate = htmlTemplate.replace("{{Adresse}}", adresse);
        htmlTemplate = htmlTemplate.replace("{{Plz}}", plz);
        htmlTemplate = htmlTemplate.replace("{{Stadt}}", stadt);
        htmlTemplate = htmlTemplate.replace("{{Telefonnummer}}", telefonnummer);
        htmlTemplate = htmlTemplate.replace("{{Geburtsdatum}}", geburtsdatum);
        htmlTemplate = htmlTemplate.replace("{{Titel}}", titel);
        htmlTemplate = htmlTemplate.replace("{{LastName}}", lastName);
        htmlTemplate = htmlTemplate.replace("{{userName}}", userName);


        return htmlTemplate;
    }
    public String getOperatingSystemFromServletRequest(HttpServletRequest servletRequest) {
        String userAgent = servletRequest.getHeader("User-Agent");
        return getOperatingSystemFromUserAgent(userAgent);
    }
    public String getOperatingSystemFromUserAgent(String userAgent) {
        String operatingSystem = "Unknown";

        if (userAgent != null) {
            if (userAgent.contains("Windows")) {
                operatingSystem = "Windows";
            } else if (userAgent.contains("Mac")) {
                operatingSystem = "Mac";
            } else if (userAgent.contains("Linux")) {
                operatingSystem = "Linux";
            } else if (userAgent.contains("Android")) {
                operatingSystem = "Android";
            } else if (userAgent.contains("iOS")) {
                operatingSystem = "iOS";
            }
        }

        return operatingSystem;
    }
}