package com.ECO.login_System.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final SpringTemplateEngine templateEngine;

    /*
     * @PostMapping
     * public ResponseEntity<String> register(@ModelAttribute RegistrationRequest
     * request) {
     * String confirmationResult = registrationService.register(request);
     * Context context = new Context();
     * context.setVariable("confirmationResult", confirmationResult);
     * String renderedHtml = templateEngine.process("registerSucsses", context);
     * // Return the rendered HTML as a response with appropriate headers and status
     * return ResponseEntity.ok().body(renderedHtml);
     * }
     * 
     */
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
}
