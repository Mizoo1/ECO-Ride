package com.ECO.controller;

import com.ECO.model.User;
import com.ECO.service.EcoService;
import com.ECO.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private final EcoService ecoService;

    public HomeController(EcoService ecoService) {
        this.ecoService = ecoService;
    }

    @GetMapping("/index")
    public String getHome() {
        return ecoService.getHome();
    }

    @GetMapping("/register")
    public String getRegister() {
        return ecoService.getRegister();
    }
    @PostMapping("/register-form")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try{
            ecoService.saveUser(user);
            redirectAttributes.addFlashAttribute("success Message", "Your form has been successfully submitted!");
            System.out.println("success Message");
            return ecoService.getTarifSeite();

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while submitting the form. Please try again later."+e.getMessage());
            System.out.println("Error Message");
            return ecoService.getRegister();
        }
    }
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = ecoService.getAllUsers();
        model.addAttribute("users", users);
        return ecoService.getAdminSeite();
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
