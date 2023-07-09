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


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;
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

                request.getEmail(),
                request.getPassword(),
                AppUserRole.ADMIN

        );

        String token = appUserService.signUpUser(appUser);

        String serverUrl = servletRequest.getRequestURL().toString();
        String link = serverUrl + "/confirm?token=" + token;

        emailSender.send(
                request.getEmail(),
                buildEmail(

                        request.getEmail(),
                        request.getPassword(),
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
    private String buildEmail(String userName, String name, String LastName, String Email, String Password, String Titel, String Adresse, String Plz, String Stadt, String Telefonnummer, String Geburtsdatum, String Geburtsort, String Fuehrerscheinnummer, String Erteilungsdatum, String Ablaufdatum, String Ausstellungsort, String Personalausweisnummer, String Reisepassnummer, String Tarif, String link, String payMethod) {
        // HTML-Datei einlesen
        String htmlTemplate = ""; // HTML-Code als String
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("email_template.html")).getFile());
            ;
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
    private String buildEmail( String Email, String Password,  String link) {
        // HTML-Datei einlesen
        String htmlTemplate = ""; // HTML-Code als String
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("email_template.html").getFile());

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                htmlTemplate += scanner.nextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        htmlTemplate = htmlTemplate.replace("{{link}}", link);
        htmlTemplate = htmlTemplate.replace("{{Email}}", Email);


        return htmlTemplate;
    }
    private String getOperatingSystemFromServletRequest(HttpServletRequest servletRequest) {
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