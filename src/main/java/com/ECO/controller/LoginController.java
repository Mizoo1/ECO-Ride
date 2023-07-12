package com.ECO.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/api/v/registration/login")
    public String showLogin_() {
        return "login";
    }

    @GetMapping("/admin/login")
    public String showAdminLogin() {
        return "login-admin";
    }

    @GetMapping("/admin/api/v/registration/logout/admin")
    public String loginControllerLogoutAdmin() {
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }

    @PostMapping("/admin/login")
    public String processAdminLogin(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                return "admin_users";
            } else {
                return "login-admin";
            }
        } else {
            return "login-admin";
        }
    }
    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
}
