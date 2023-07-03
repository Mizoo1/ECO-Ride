package com.ECO.service;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.repository.EcoRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class EcoService {
    private final EcoRepository ecoRepository;

    public EcoService(EcoRepository ecoRepository) {
        this.ecoRepository = ecoRepository;
    }

    public ModelAndView getHome(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("index");
        String firstname = userDetails.getFirstName();
        modelAndView.addObject("firstname", firstname);
        return modelAndView;
    }
    public ModelAndView zeigeProfil(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("profile1");
        String firstname = userDetails.getFirstName();
        modelAndView.addObject("firstname", firstname);
        modelAndView.addObject("userDetails", userDetails);
        return modelAndView;
    }
    public String getHomeWithoutLogin() {
        return ecoRepository.getHomeWithoutLogin();
    }

    public String getRegister() {
        return ecoRepository.getRegister();
    }

    public String getLogout() {
        return ecoRepository.getLogout();
    }

    public ModelAndView getContact(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("contact");
        String firstname = userDetails.getFirstName();
        modelAndView.addObject("firstname", firstname);
        return modelAndView;
    }
    public ModelAndView getReservierung(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("reservierung");
        String firstname = userDetails.getFirstName();
        modelAndView.addObject("firstname", firstname);
        return modelAndView;
    }
    public String getContactWithoutLogin() {
        return ecoRepository.getContactWithoutLogin();
    }

    public ModelAndView getAbout(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("about");
        String firstname = userDetails.getFirstName();
        modelAndView.addObject("firstname", firstname);
        return modelAndView;
    }
    public String getAboutWithoutLogin() {
        return ecoRepository.getAboutWithoutLogin();
    }

    public ModelAndView getServices(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("services");
        String firstname = userDetails.getFirstName();
        modelAndView.addObject("firstname", firstname);
        return modelAndView;
    }
    public String getServicesWithoutLogin() {
        return ecoRepository.getServicesWithoutLogin();
    }


}
