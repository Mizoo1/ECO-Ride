package com.ECO.repository;

import org.springframework.stereotype.Repository;

@Repository
public class EcoRepository {


    public String getLogout() {
        return "logout";
    }




    public String getAboutWithoutLogin() {
        return "aboutWithoutLogin";
    }


    public String getServicesWithoutLogin() {
        return "serviceWithoutLogin";
    }
}
