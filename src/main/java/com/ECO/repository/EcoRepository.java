package com.ECO.repository;

import org.springframework.stereotype.Repository;

@Repository
public class EcoRepository {


    public String getHomeWithoutLogin() {
        return "indexWithoutLogin";
    }

    public String getRegister() {
        return "register";
    }


    public String getLogout() {
        return "logout";
    }


    public String getContactWithoutLogin() {
        return "contactWithoutLogin";
    }

    public String getAboutWithoutLogin() {
        return "aboutWithoutLogin";
    }


    public String getServicesWithoutLogin() {
        return "serviceWithoutLogin";
    }
}
