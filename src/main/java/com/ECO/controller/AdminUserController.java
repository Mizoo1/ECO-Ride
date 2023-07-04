package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AdminUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/users")
    public String showAdminUsersPage(Model model) {
        List<AppUser> users = appUserRepository.findAll();
        model.addAttribute("users", users);

        // Rufen Sie alle Tarifarten ab und fügen Sie sie dem Model hinzu
        List<String> tarifTypes = appUserRepository.getAllTarifTypes();
        model.addAttribute("tarifTypes", tarifTypes);

        return "admin_users";
    }


    @GetMapping("/users/search")
    public String searchUsers(@RequestParam("criteria") String criteria, @RequestParam("keyword") String keyword, Model model) {
        List<AppUser> users;
        if (criteria.equals("id")) {
            try {
                Long id = Long.parseLong(keyword);
                users = appUserRepository.searchUsersByCriteria(keyword, id);
            } catch (NumberFormatException e) {
                // Handle the case where the keyword is not a valid Long value
                users = new ArrayList<>(); // Or any other appropriate action
            }
        } else {
            users = appUserRepository.searchUsersByCriteria(keyword, null);
        }
        model.addAttribute("users", users);
        return "admin_users";
    }





    @GetMapping("/users/{id}")
    public String showUserDetails(@PathVariable("id") Long id, Model model) {
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            model.addAttribute("user", user);
            return "user_details";
        } else {
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        appUserRepository.deleteById(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/bookings/statistics")
    public ResponseEntity<List<Object[]>> getBookingStatistics() {
        List<Object[]> bookingStatistics = appUserRepository.countBookingsByTarif();
        return ResponseEntity.ok(bookingStatistics);
    }

}
