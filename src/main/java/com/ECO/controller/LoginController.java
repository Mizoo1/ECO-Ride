package com.ECO.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLogin()
    {
        return "login";
    }
    @GetMapping("/api/v/registration/login")
    public String showLogin_()
    {
        return "login";
    }

}
