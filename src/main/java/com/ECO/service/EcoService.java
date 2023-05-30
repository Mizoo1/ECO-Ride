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


}
