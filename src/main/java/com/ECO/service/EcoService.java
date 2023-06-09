package com.ECO.service;

import com.ECO.repository.EcoRepository;
import org.springframework.stereotype.Service;

@Service
public class EcoService {
    private final EcoRepository ecoRepository;

    public EcoService(EcoRepository ecoRepository) {
        this.ecoRepository = ecoRepository;
    }

    public String getHome() {
        return ecoRepository.getHome();
    }
    public String getHomeWithoutLogin() {
        return ecoRepository.getHomeWithoutLogin();
    }

    public String getRegister() {
        return ecoRepository.getRegister();
    }
    public String getLogin() {
        return ecoRepository.getLogin();
    }

    public String getContact() {
        return ecoRepository.getContact();
    }

    public String getAbout() {
        return ecoRepository.getAbout();
    }

    public String getServices() {
        return ecoRepository.getServices();
    }
}
