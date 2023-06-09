package com.ECO.repository;

import org.springframework.stereotype.Repository;

@Repository
public class EcoRepository {

    public String getHome() {
        return "index";
    }
    public String getHomeWithoutLogin() {
        return "indexWithoutLogin";
    }

    public String getRegister() {
        return "register";
    }

    public String getLogin() {
        return "login";
    }

    public String getContact() {
        return "contact";
    }

    public String getAbout() {
        return "about";
    }

    public String getServices() {
        return "services";
    }
}
