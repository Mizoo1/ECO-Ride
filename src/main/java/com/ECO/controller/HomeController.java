package com.ECO.controller;

import com.ECO.login_System.registration.RegistrationRequest;
import com.ECO.login_System.registration.RegistrationService;
import com.ECO.service.EcoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import java.util.List;


@Controller
public class HomeController {

    private final EcoService ecoService;
    private final RegistrationService registrationService;
    private final SpringTemplateEngine templateEngine;

    public HomeController(EcoService ecoService,RegistrationService registrationService,SpringTemplateEngine templateEngine) {
        this.ecoService = ecoService;
        this.registrationService = registrationService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/api/v/registration/index")
    public String getHomeWithoutLogin() {
        return ecoService.getHome();
    }

    @GetMapping("index")
    public String getHome() {
        return ecoService.getHome();
    }
    @GetMapping("/api/v/registration/register")
    public String getRegister() {
        return ecoService.getRegister();
    }
    @GetMapping("/login")
    public String getLogin() {
        return ecoService.getLogin();
    }
    @GetMapping("/contact")
    public String getContact() {
        return ecoService.getContact();
    }
    @GetMapping("/services")
    public String getServices() {
        return ecoService.getServices();
    }
    @GetMapping("/about")
    public String getAbout() {
        return ecoService.getAbout();
    }
    @PostMapping(path = "api/v1/registration")
    public ResponseEntity<String> register(@ModelAttribute RegistrationRequest request) {
        String confirmationResult = registrationService.register(request);
        Context context = new Context();
        context.setVariable("confirmationResult", confirmationResult);
        String renderedHtml = templateEngine.process("registerSucsses", context);
        // Return the rendered HTML as a response with appropriate headers and status
        return ResponseEntity.ok().body(renderedHtml);
    }
    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token, Model model) {
        String confirmationResult = registrationService.confirmToken(token);
        // Create a Thymeleaf context and add necessary data
        Context context = new Context();
        context.setVariable("confirmationResult", confirmationResult);

        // Render the HTML template
        String renderedHtml = templateEngine.process("confirmed", context);

        // Return the rendered HTML as a response with appropriate headers and status
        return ResponseEntity.ok().body(renderedHtml);
    }
    @GetMapping()
    public String registerDone() {
        return "sucess";
    }

}
