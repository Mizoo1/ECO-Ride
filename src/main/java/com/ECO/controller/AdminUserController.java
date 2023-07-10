package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserRepository;
import com.ECO.login_System.appuser.Reservation;
import com.ECO.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final AppUserRepository appUserRepository;
    private final ReservationRepository reservationRepository;
    @Autowired
    public AdminUserController(AppUserRepository appUserRepository, ReservationRepository reservationRepository) {
        this.appUserRepository = appUserRepository;
        this.reservationRepository = reservationRepository;
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
            return "edit_user";
        } else {
            return "redirect:/admin/users";
        }
    }


    @GetMapping("/bookings/statistics")
    public ResponseEntity<List<Object[]>> getBookingStatistics() {
        List<Object[]> bookingStatistics = appUserRepository.countBookingsByTarif();
        return ResponseEntity.ok(bookingStatistics);
    }

    @GetMapping("/users/payment-methods")
    public ResponseEntity<List<Object[]>> getPaymentMethodStatistics() {
        List<Object[]> paymentMethodStatistics = appUserRepository.countPaymentMethods();
        return ResponseEntity.ok(paymentMethodStatistics);
    }

    @GetMapping("/users/age-statistics")
    public ResponseEntity<List<Object[]>> getAgeStatistics() {
        List<Object[]> ageStatistics = appUserRepository.calculateAgeStatistics();
        return ResponseEntity.ok(ageStatistics);
    }
    @GetMapping("/users/operating-systems")
    public ResponseEntity<List<Object[]>> getOperatingSystemStatistics() {
        List<Object[]> operatingSystemStatistics = appUserRepository.countOperatingSystems();
        return ResponseEntity.ok(operatingSystemStatistics);
    }

    @GetMapping("/users/vehicle-types")
    public ResponseEntity<List<Object[]>> getVehicleTypeStatistics() {
        List<Object[]> vehicleTypeStatistics = appUserRepository.countVehicleTypes();
        return ResponseEntity.ok(vehicleTypeStatistics);
    }
    @GetMapping("/users/{id}/kunden-profile")
    public String showUserProfile(@PathVariable("id") Long id, Model model) {
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            model.addAttribute("user", user);

            // Find user's reservations
            List<Reservation> reservations = reservationRepository.findByAppUser(user);
            model.addAttribute("reservations", reservations);

            return "kunden-profile";
        } else {
            return "redirect:/admin/users";
        }
    }
    @PostMapping("/users/{id}/kunden-profile/update")
    public String updateUserProfile(@PathVariable("id") Long id, @ModelAttribute AppUser updatedUser) {
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            // Update user information
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());

            appUserRepository.save(user);
            return "redirect:/admin/users/" + id + "/kunden-profile";
        } else {
            return "redirect:/admin/users";
        }
    }
    @GetMapping("/users/{id}/edit")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            model.addAttribute("user", user); // Stellen Sie sicher, dass "user" als Vorlagenattribut hinzugefügt wird
            return "edit_user";
        } else {
            return "redirect:/admin/users";
        }
    }
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id, Principal principal) {
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();

            // Benutzer löschen
            appUserRepository.delete(user);
        }
        return "redirect:/admin/users";
    }
    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute AppUser appUser, Principal principal) {
        Optional<AppUser> optionalUser = appUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            // Update user information based on the form data
            user.setFirstName(appUser.getFirstName());
            user.setLastName(appUser.getLastName());
            // Continue for all fields you want to update...

            appUserRepository.save(user);

        }
        return "redirect:/admin/users/" + id;
    }

}
