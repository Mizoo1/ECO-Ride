package com.ECO.controller;

import com.ECO.service.EcoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;


@Controller
public class HomeController {

    private final EcoService ecoService;

    public HomeController(EcoService ecoService) {
        this.ecoService = ecoService;
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

}
