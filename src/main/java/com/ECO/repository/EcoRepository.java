package com.ECO.repository;

import org.springframework.stereotype.Repository;

@Repository
public class EcoRepository {

    private final String home = "index";

    public String getHome() {
        return home;
    }

}
