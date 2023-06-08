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


import java.time.LocalDateTime;

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
                        AppUserRole.USER

                )
        );

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(),
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
                        , link));

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

    private String buildEmail(String name,
                              String LastName,
                            String Email,
                            String Password,
                            String Titel,
                            String Adresse,
                            String Plz,
                            String Stadt,
                            String  Telefonnummer,
                            String Geburtsdatum,
                            String Geburtsort,
                            String Fuehrerscheinnummer,
                            String Erteilungsdatum,
                            String Ablaufdatum,
                            String Ausstellungsort,
                            String Personalausweisnummer,
                            String Reisepassnummer,
                            String Tarif, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Bestätigen Sie Ihre E-Mail</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hallo " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Vielen Dank für Ihre Registrierung. Bitte klicken Sie auf den untenstehenden Link, um Ihr Konto zu aktivieren:</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Jetzt aktivieren</a> </p></blockquote>\n Der Link läuft in 15 Minuten ab. <p>Bis bald</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>"+
                "    <title>Vertragsmuster</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Vertragsmuster</h1>\n" +
                "    \n" +
                "    <p>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Sehr geehrte/r "+ Titel +" " +name + " "+LastName+  ",</p>"+
                "    </p>\n" +
                "    \n" +
                "    <p>\n" +
                "        Vielen Dank für Ihr Interesse an unserem Vertra. Um den Vertrag abzuschließen, benötigen wir einige Informationen von Ihnen:\n" +
                "    </p>\n" +
                "    \n" +
                "    <p>\n" +
                "        <strong>Persönliche Informationen:</strong><br>\n" +
                "        Email: \n"+Email+"<br>\n" +
                "        Adresse: "+Adresse+", "+Plz +" "+Stadt+"<br>\n" +
                "        Telefonnummer: "+Telefonnummer+"<br>\n" +
                "        Geburtsdatum: "+Geburtsdatum+"<br>\n" +
                "        Geburtsort: "+Geburtsort+"<br>\n" +
                "        Führerscheinnummer: "+Fuehrerscheinnummer+"<br>\n" +
                "        Erteilungsdatum: "+Erteilungsdatum+"<br>\n" +
                "        Ablaufdatum: "+Ablaufdatum+"<br>\n" +
                "        Ausstellungsort: "+Ausstellungsort+"<br>\n" +
                "        Personalausweisnummer: "+Personalausweisnummer+"<br>\n" +
                "        Reisepassnummer: "+Reisepassnummer+"\n" +
                "    </p>\n" +
                "    \n" +
                "    <p>\n" +
                "        Bitte überprüfen Sie die oben aufgeführten Informationen sorgfältig und stellen Sie sicher, dass sie korrekt sind. Falls Sie Änderungen vornehmen möchten, teilen Sie uns diese bitte umgehend mit.\n" +
                "    </p>\n" +
                "    \n" +
                "    <p>\n" +
                "        Der ausgewählte Tarif für den Vertrag ist: "+Tarif+".\n" +
                "    </p>\n" +
                "    \n" +
                "    </p>\n" +
                "    \n" +
                "    <p>\n" +
                "        Bei Fragen oder Unklarheiten können Sie sich jederzeit an uns wenden.\n" +
                "    </p>\n" +
                "    \n" +
                "    <p>\n" +
                "        Vielen Dank und freundliche Grüße,"+"\n" +
                "        Ihr Vertragsmuster-Team\n" + "ECO-Ride GmbH" +
                "    </p>\n";
    }
}