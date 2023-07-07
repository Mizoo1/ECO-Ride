package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserRepository;
import com.ECO.login_System.appuser.AppUserService;
import com.ECO.login_System.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
public class UserController {

    private final AppUserService appUserService;
    private AppUserRepository appUserRepository;
    private RegistrationService registrationService;
    @Autowired
    public UserController(AppUserService appUserService,AppUserRepository appUserRepository,RegistrationService registrationService) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
        this.registrationService = registrationService;
    }

    @PutMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestBody AppUser appUser, @Autowired HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String operatingSystem = registrationService.getOperatingSystemFromUserAgent(userAgent);
        appUser.setOperatingSystem(operatingSystem);
        appUserService.updateAppUser(id, appUser);
        return "redirect:/user/{id}";
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        // Check if the user exists
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            // Delete the user
            appUserRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
