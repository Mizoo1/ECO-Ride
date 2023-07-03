package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    private final AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PutMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestBody AppUser appUser) {
        appUserService.updateAppUser(id, appUser);
        return "redirect:/user/{id}";
    }
}
