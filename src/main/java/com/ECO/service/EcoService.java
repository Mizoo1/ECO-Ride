package com.ECO.service;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.repository.EcoRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class EcoService
{
    private final EcoRepository ecoRepository;
    private final JavaMailSender mailSender;
    public EcoService(EcoRepository ecoRepository, JavaMailSender mailSender)
    {
        this.ecoRepository = ecoRepository;
        this.mailSender = mailSender;
    }
    public ModelAndView getHome(Authentication authentication)
    {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("index");
        String userName = userDetails.getUserName();
        modelAndView.addObject("userName", userName);
        return modelAndView;
    }
    public ModelAndView zeigeProfil(Authentication authentication)
    {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("profile");
        String userName = userDetails.getUserName();
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("userDetails", userDetails);
        return modelAndView;
    }
    public String getHomeWithoutLogin()
    {
        return "indexWithoutLogin";
    }
    public String getRegister()
    {
        return "register";
    }
    public String getRegisterAdmin()
    {
        return "registerAdmin";
    }
    public String getLogout()
    {
        return ecoRepository.getLogout();
    }
    public ModelAndView getContact(Authentication authentication)
    {
        // Extrahiere den authentifizierten Benutzer aus der Authentifizierungsinformation
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("contact");
        String userName = userDetails.getUserName();
        // Füge den Benutzernamen und die Benutzerdetails zum ModelAndView hinzu,
        // um sie an die Kontaktseite zu übergeben
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("userDetails", userDetails);
        return modelAndView;
    }
    public String getContactWithoutLogin()
    {
        return "contactWithoutLogin";
    }

    public ModelAndView getAbout(Authentication authentication)
    {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("about");
        String userName = userDetails.getUserName();
        modelAndView.addObject("userName", userName);
        return modelAndView;
    }
    public String getAboutWithoutLogin()
    {
        return "aboutWithoutLogin";
    }
    public ModelAndView getServices(Authentication authentication)
    {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("services");
        String userName = userDetails.getUserName();
        modelAndView.addObject("userName", userName);
        return modelAndView;
    }
    public String getServicesWithoutLogin()
    {
        return "serviceWithoutLogin";
    }
}
