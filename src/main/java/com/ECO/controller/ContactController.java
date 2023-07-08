package com.ECO.controller;

import com.ECO.login_System.registration.RegistrationService;
import com.ECO.service.ContactService;
import com.ECO.service.EcoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;
@Controller
public class ContactController {

    private final EcoService ecoService;
    private final ContactService contactService;
    private final SpringTemplateEngine templateEngine;

    public ContactController(EcoService ecoService, ContactService contactService, RegistrationService registrationService, SpringTemplateEngine templateEngine) {
        this.ecoService = ecoService;
        this.contactService = contactService;
        this.templateEngine = templateEngine;
    }

    @PostMapping("/api/v/registration/submit_form")
    public String submitFormWithoutLogin(
            @RequestParam("name") String name, @RequestParam("vorname") String vorname,
            @RequestParam(value = "adresse", required = false) String adresse,
            @RequestParam(value = "telefon", required = false) String telefon,
            @RequestParam(value = "mobil", required = false) String mobil,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam("message") String message) {

        // Überprüfen Sie die Länge der Nachricht auf maximal 150 Buchstaben
        if (message.length() > 150) {
            return "redirect:/api/v/registration/contact?error=too_many_characters"; // Weiterleitung zur Kontaktseite mit Fehlermeldung
        }

        // E-Mail senden oder andere Verarbeitung der Formulardaten durchführen
        contactService.sendEmailContatWithoutLogin(name, vorname, adresse, telefon, mobil, email, message);

        return "redirect:/api/v/registration/contact?success"; // Weiterleitung zur Kontaktseite mit Erfolgsmeldung
    }
    @PostMapping("/submit_form")
    public String submitForm( @RequestParam("id") Long id,
                              @RequestParam("name") String name, @RequestParam("vorname") String vorname,
                              @RequestParam(value = "adresse", required = false) String adresse,
                              @RequestParam(value = "telefon", required = false) String telefon,
                              @RequestParam(value = "mobil", required = false) String mobil,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam("message") String message) {
        if (message.length() > 150) {
            return "redirect:/contact?error=too_many_characters";
        }
        contactService.sendEmailContact(id,name, vorname, adresse, telefon, mobil, email, message);
        return "redirect:/contact?success";
    }
}
