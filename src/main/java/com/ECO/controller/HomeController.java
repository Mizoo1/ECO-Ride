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

    private final EcoService ecoService;

    public HomeController(EcoService ecoService) {
        this.ecoService = ecoService;
    }

    @GetMapping("/index")
    public String getHome() {
        return ecoService.getHome();
    }

}
