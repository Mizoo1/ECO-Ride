package com.ECO.login_System.registration;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final SpringTemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;
/*
    @PostMapping
    public ResponseEntity<String> register(@ModelAttribute RegistrationRequest request) {
        String confirmationResult = registrationService.register(request);
        Context context = new Context();
        context.setVariable("confirmationResult", confirmationResult);
        String renderedHtml = templateEngine.process("registerSucsses", context);
        // Return the rendered HTML as a response with appropriate headers and status
        return ResponseEntity.ok().body(renderedHtml);
    }

 */
@GetMapping(path = "confirm")
public ResponseEntity<String> confirm(@RequestParam("token") String token)
    {
        String confirmationResult = registrationService.confirmToken(token);
        Context context = new Context();
        context.setVariable("confirmationResult", confirmationResult);
        String renderedHtml = templateEngine.process("confirmed", context);
        return ResponseEntity.ok().body(renderedHtml);
    }
}
