package com.ECO.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.ECO.login_System.registration.RegistrationRequest;
import com.ECO.login_System.registration.RegistrationService;
import com.ECO.service.EcoService;

@Controller
public class HomeController {

    private final EcoService ecoService;
    private final RegistrationService registrationService;
    private final SpringTemplateEngine templateEngine;

    public HomeController(EcoService ecoService, RegistrationService registrationService,
            SpringTemplateEngine templateEngine) {
        this.ecoService = ecoService;
        this.registrationService = registrationService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/api/v/registration/index")
    public String getHomeWithoutLogin(Model model, HttpSession session) {
        // model.addAttribute("loggedIn", session.getAttribute("loggedIn"));
        return ecoService.getHomeWithoutLogin();
    }

    @GetMapping("index")
    public String getHome(Model model, HttpSession session) {
        // model.addAttribute("logout", session.getAttribute("logout"));
        return ecoService.getHome();
    }

    @GetMapping("/api/v/registration/register")
    public String getRegister() {
        return ecoService.getRegister();
    }

    @GetMapping("/register")
    public String getPersonlicheSeite() {
        return "/name";
    }

    @GetMapping("/api/v/registration/login")
    public String getLogin() {
        return ecoService.getLogin();
    }

    @GetMapping("/api/v/registration/logout")
    public String getLogout() {
        return ecoService.getLogout();
    }

    @GetMapping("/api/v/registration/contact")
    public String getContact() {
        return ecoService.getContact();
    }

    @GetMapping("/contact")
    public String getContactWithoutLogin() {
        return ecoService.getContact();
    }

    @GetMapping("/api/v/registration/services")
    public String getServices() {
        return ecoService.getServices();
    }

    @GetMapping("/services")
    public String getServicesWithoutLogin() {
        return ecoService.getServices();
    }

    @GetMapping("/api/v/registration/about")
    public String getAbout() {
        return ecoService.getAbout();
    }

    @GetMapping("/about")
    public String getAboutWithoutLogin() {
        return ecoService.getAbout();
    }

    @PostMapping(path = "api/v1/registration")
    public ResponseEntity<String> register(@ModelAttribute RegistrationRequest request) {
        String confirmationResult = registrationService.register(request);
        Context context = new Context();
        context.setVariable("confirmationResult", confirmationResult);
        String renderedHtml = templateEngine.process("/registerSucsses", context);
        // Return the rendered HTML as a response with appropriate headers and status
        return ResponseEntity.ok().body(renderedHtml);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam String token, Model model) {
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

    @GetMapping(path = "/api/v/registration/term_condition")
    public String getTerm_condition() {
        return "term_condition";
    }

}
