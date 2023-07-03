package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserService;
import com.ECO.login_System.appuser.PersonalDataUpdateRequest;
import com.ECO.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final AppUserService appUserService;
    @Autowired
    public UserController( AppUserService appUserService) {
        this.appUserService = appUserService;

    }

    @PutMapping("/updateUser/{id}") // Assuming you have a path variable for the user ID
    public String updateUser(@PathVariable Long id, @RequestBody AppUser appUser) {
        // Update the user details by passing the user ID
        appUserService.updateAppUser(id, appUser);
        // Redirect or return a response accordingly
        return "redirect:/user/{id}"; // Assuming you have a user details page to display after updating
    }
    @PutMapping("/{email}/personal-data")
    public ResponseEntity<AppUser> updatePersonalData(
            @PathVariable("email") String email,
            @RequestBody PersonalDataUpdateRequest request) {
        AppUser updatedUser = appUserService.updatePersonalData(email, request);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/{email}/personal-data")
    public ResponseEntity<AppUser> getPersonalData(
            @PathVariable("email") String email) {
        Optional<AppUser> optionalUser = appUserService.findByEmail(email);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
