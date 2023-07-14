package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserRepository;
import com.ECO.login_System.appuser.Reservation;
import com.ECO.login_System.appuser.ReservationStatus;
import com.ECO.repository.ReservationRepository;
import com.ECO.service.EcoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminUserControllerTest
{
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private EcoService ecoService;
    @Mock
    private Model model;
    @InjectMocks
    private AdminUserController adminUserController;

    @Mock
    private Principal principal;
    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Testet die Methode, die die Administratoren seite mit Benutzerinformationen anzeigt.
     */
    @Test
    public void testShowAdminUsersPage()
    {
        List<AppUser> users = new ArrayList<>();
        users.add(new AppUser());
        users.add(new AppUser());

        List<String> tarifTypes = new ArrayList<>();
        tarifTypes.add("Basic");
        tarifTypes.add("Premium");
        tarifTypes.add("Ultra");
        when(appUserRepository.findAll()).thenReturn(users);
        when(appUserRepository.getAllTarifTypes()).thenReturn(tarifTypes);
        String viewName = adminUserController.showAdminUsersPage(model);
        verify(appUserRepository).findAll();
        verify(appUserRepository).getAllTarifTypes();
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("tarifTypes", tarifTypes);
        assertEquals("admin_users", viewName);
    }
    /**
     * Testet die Methode zum Suchen von Benutzern anhand ihrer ID.
     */
    @Test
    public void testSearchUsersById()
    {
        String criteria = "id";
        String keyword = "123";
        Long id = 123L;
        List<AppUser> users = new ArrayList<>();
        users.add(new AppUser());
        when(appUserRepository.searchUsersByCriteria(keyword, id)).thenReturn(users);
        String viewName = adminUserController.searchUsers(criteria, keyword, model);
        verify(appUserRepository).searchUsersByCriteria(keyword, id);
        verify(model).addAttribute("users", users);
        assertEquals("admin_users", viewName);
    }
    /**
     * Testet die Methode zum Suchen von Benutzern anhand eines anderen Kriteriums.
     */
    @Test
    public void testSearchUsersByOtherCriteria()
    {
        String criteria = "name";
        String keyword = "Muaaz";
        List<AppUser> users = new ArrayList<>();
        users.add(new AppUser());
        when(appUserRepository.searchUsersByCriteria(keyword, null)).thenReturn(users);
        String viewName = adminUserController.searchUsers(criteria, keyword, model);
        verify(appUserRepository).searchUsersByCriteria(keyword, null);
        verify(model).addAttribute("users", users);
        assertEquals("admin_users", viewName);
    }
    /**
     * Testet die Methode zum Anzeigen der Benutzerdetails anhand einer gültigen Benutzer-ID.
     */
    @Test
    public void testShowUserDetailsWithValidId()
    {

        Long userId = 123L;
        AppUser user = new AppUser();

        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        String viewName = adminUserController.showUserDetails(userId, model);
        verify(appUserRepository).findById(userId);
        verify(model, times(2)).addAttribute("user", user);
        assertEquals("edit_user", viewName);
    }
    /**
     * Testet die Methode zum Anzeigen der Benutzerdetails anhand einer ungültigen Benutzer-ID.
     */
    @Test
    public void testShowUserDetailsWithInvalidId()
    {

        Long userId = 123L;
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());
        String viewName = adminUserController.showUserDetails(userId, model);
        verify(appUserRepository).findById(userId);
        assertEquals("redirect:/admin/users", viewName);
    }
    /**
     * Testet die Methode zum Abrufen der Buchungsstatistiken.
     */
    @Test
    public void testGetBookingStatistics()
    {

        List<Object[]> bookingStatistics = new ArrayList<>();
        bookingStatistics.add(new Object[]{"Basic", 10});
        bookingStatistics.add(new Object[]{"Ultra", 5});
        bookingStatistics.add(new Object[]{"Premium", 8});
        when(appUserRepository.countBookingsByTarif()).thenReturn(bookingStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getBookingStatistics();
        verify(appUserRepository).countBookingsByTarif();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookingStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Abrufen der Standortstatistiken.
     */
    @Test
    public void testGetLocationStatistics()
    {
        List<Object[]> locationStatistics = new ArrayList<>();
        locationStatistics.add(new Object[]{"Bremen", 10});
        locationStatistics.add(new Object[]{"Hamburg", 5});
        locationStatistics.add(new Object[]{"Zeven", 9});
        locationStatistics.add(new Object[]{"Tarmstedt", 25});
        locationStatistics.add(new Object[]{"Bremen Nord", 33});
        locationStatistics.add(new Object[]{"Bremen Mitte", 2});
        locationStatistics.add(new Object[]{"Burbach", 17});
        locationStatistics.add(new Object[]{"Soest", 2});
        locationStatistics.add(new Object[]{"Lippstadt", 1});
        locationStatistics.add(new Object[]{"Kiel", 69});
        locationStatistics.add(new Object[]{"Bremer hafen", 47});
        when(reservationRepository.countLocations()).thenReturn(locationStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getLocationStatistics();
        verify(reservationRepository).countLocations();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(locationStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Abrufen der Statistiken für Zahlungsmethode und Datum.
     */
    @Test
    public void testGetPaymentMethodDateStatistics()
    {
        List<Object[]> paymentMethodDateStatistics = new ArrayList<>();
        paymentMethodDateStatistics.add(new Object[]{"Paypal", "2023-07-14", 10});
        paymentMethodDateStatistics.add(new Object[]{"Visa", "2023-07-15", 5});
        paymentMethodDateStatistics.add(new Object[]{"MasterCard", "2023-07-18", 12});
        when(reservationRepository.countReservationsByPaymentMethodAndDate()).thenReturn(paymentMethodDateStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getPaymentMethodDateStatistics();
        verify(reservationRepository).countReservationsByPaymentMethodAndDate();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(paymentMethodDateStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Abrufen der Statistiken für Zahlungsmethode und Uhrzeit.
     */
    @Test
    public void testGetPaymentMethodTimeStatistics()
    {
        List<Object[]> paymentMethodTimeStatistics = new ArrayList<>();
        paymentMethodTimeStatistics.add(new Object[]{"Payment Method A", "10:00 AM", 10});
        paymentMethodTimeStatistics.add(new Object[]{"Payment Method B", "02:00 PM", 5});
        when(reservationRepository.countReservationsByPaymentMethodAndTime()).thenReturn(paymentMethodTimeStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getPaymentMethodTimeStatistics();
        verify(reservationRepository).countReservationsByPaymentMethodAndTime();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(paymentMethodTimeStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Abrufen der Statistiken für Zahlungsmethoden.
     */
    @Test
    public void testGetPaymentMethodStatistics()
    {
        List<Object[]> paymentMethodStatistics = new ArrayList<>();
        paymentMethodStatistics.add(new Object[]{"Payment Method A", 10});
        paymentMethodStatistics.add(new Object[]{"Payment Method B", 5});
        when(appUserRepository.countPaymentMethods()).thenReturn(paymentMethodStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getPaymentMethodStatistics();
        verify(appUserRepository).countPaymentMethods();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(paymentMethodStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Abrufen der Altersstatistiken.
     */
    @Test
    public void testGetAgeStatistics()
    {
        List<Object[]> ageStatistics = new ArrayList<>();
        ageStatistics.add(new Object[]{25, 10});
        ageStatistics.add(new Object[]{30, 5});
        when(appUserRepository.calculateAgeStatistics()).thenReturn(ageStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getAgeStatistics();
        verify(appUserRepository).calculateAgeStatistics();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ageStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Abrufen der Statistiken für Betriebssysteme.
     */
    @Test
    public void testGetOperatingSystemStatistics()
    {
        List<Object[]> operatingSystemStatistics = new ArrayList<>();
        operatingSystemStatistics.add(new Object[]{"Windows", 10});
        operatingSystemStatistics.add(new Object[]{"Linux", 5});
        operatingSystemStatistics.add(new Object[]{"Mac", 2});
        when(appUserRepository.countOperatingSystems()).thenReturn(operatingSystemStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getOperatingSystemStatistics();
        verify(appUserRepository).countOperatingSystems();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(operatingSystemStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Abrufen der Statistiken für Fahrzeugtypen.
     */
    @Test
    public void testGetVehicleTypeStatistics()
    {
        List<Object[]> vehicleTypeStatistics = new ArrayList<>();
        vehicleTypeStatistics.add(new Object[]{"PKW", 10});
        vehicleTypeStatistics.add(new Object[]{"LKW", 5});
        vehicleTypeStatistics.add(new Object[]{"ANHÄNGER", 2});
        vehicleTypeStatistics.add(new Object[]{"Motored", 15});
        when(appUserRepository.countVehicleTypes()).thenReturn(vehicleTypeStatistics);
        ResponseEntity<List<Object[]>> response = adminUserController.getVehicleTypeStatistics();
        verify(appUserRepository).countVehicleTypes();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(vehicleTypeStatistics, response.getBody());
    }
    /**
     * Testet die Methode zum Anzeigen des Benutzerprofils anhand einer gültigen Benutzer-ID.
     */
    @Test
    public void testShowUserProfileWithValidId()
    {
        Long userId = 123L;
        AppUser user = new AppUser();
        List<Reservation> reservations = new ArrayList<>();
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reservationRepository.findByAppUser(user)).thenReturn(reservations);
        String viewName = adminUserController.showUserProfile(userId, model);
        verify(appUserRepository).findById(userId);
        verify(reservationRepository).findByAppUser(user);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("reservations", reservations);
        assertEquals("kunden-profile", viewName);
    }
    /**
     * Testet die Methode zum Anzeigen des Benutzerprofils anhand einer ungültigen Benutzer-ID.
     */
    @Test
    public void testShowUserProfileWithInvalidId()
    {
        Long userId = 123L;
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());
        String viewName = adminUserController.showUserProfile(userId, model);
        verify(appUserRepository).findById(userId);
        assertEquals("redirect:/admin/users/", viewName);
    }
    /**
     * Testet die Methode zum Anzeigen des Bearbeitungsformulars für einen Benutzer
     * anhand einer gültigen Benutzer-ID.
     */
    @Test
    public void testUpdateUserProfileWithValidId()
    {
        Long userId = 123L;
        String firstName = "Muaaz";
        String lastName = "Bdear";
        AppUser updatedUser = new AppUser();
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        AppUser user = new AppUser();
        user.setId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(appUserRepository.save(user)).thenReturn(user);
        String redirectUrl = adminUserController.updateUserProfile(userId, updatedUser);
        verify(appUserRepository).findById(userId);
        verify(appUserRepository).save(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals("redirect:/admin/users/" + userId + "/kunden-profile", redirectUrl);
    }
    /**
     * Testet die Methode zum Anzeigen des Bearbeitungsformulars für einen Benutzer
     * anhand einer ungültigen Benutzer-ID.
     */
    @Test
    public void testUpdateUserProfileWithInvalidId()
    {
        Long userId = 123L;
        AppUser updatedUser = new AppUser();
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());
        String redirectUrl = adminUserController.updateUserProfile(userId, updatedUser);
        verify(appUserRepository).findById(userId);
        assertEquals("redirect:/admin/users/", redirectUrl);
    }
    /**
     * Testet die Methode zum Anzeigen des Bearbeitungsformulars
     * für einen Benutzer anhand einer gültigen Benutzer-ID.
     */
    @Test
    public void testShowEditUserFormWithValidId()
    {
        Long userId = 123L;
        AppUser user = new AppUser();
        user.setId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        String viewName = adminUserController.showEditUserForm(userId, model);
        verify(appUserRepository).findById(userId);
        verify(model).addAttribute("user", user);
        assertEquals("edit_user", viewName);
    }
    /**
     * Testet die Methode zum Anzeigen des Bearbeitungsformulars
     * für einen Benutzer anhand einer ungültigen Benutzer-ID.
     */
    @Test
    public void testShowEditUserFormWithInvalidId()
    {
        Long userId = 123L;
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());
        String viewName = adminUserController.showEditUserForm(userId, model);
        verify(appUserRepository).findById(userId);
        assertEquals("redirect:/admin/users/", viewName);
    }
    /**
     * Testet die Methode zum Löschen eines Benutzers anhand einer gültigen Benutzer-ID.
     */
    @Test
    public void testDeleteUserWithValidId()
    {
        Long userId = 123L;
        AppUser user = new AppUser();
        user.setId(userId);
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        String redirectUrl = adminUserController.deleteUser(userId, principal);
        verify(appUserRepository).findById(userId);
        verify(appUserRepository).delete(user);
        assertEquals("redirect:/admin/users/", redirectUrl);
    }
    /**
     * Testet die Methode zum Löschen eines Benutzers anhand einer ungültigen Benutzer-ID.
     */
    @Test
    public void testDeleteUserWithInvalidId() {

        Long userId = 123L;
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());
        String redirectUrl = adminUserController.deleteUser(userId, principal);
        verify(appUserRepository).findById(userId);
        assertEquals("redirect:/admin/users/", redirectUrl);
    }
    /**
     * Testet die Methode zum Aktualisieren eines Benutzers anhand einer gültigen Benutzer-ID.
     */
    @Test
    public void testUpdateUserWithValidId()
    {
        Long userId = 123L;
        AppUser user = new AppUser();
        user.setId(userId);
        AppUser updatedUser = new AppUser();
        updatedUser.setFirstName("Muaaz");
        updatedUser.setLastName("Bdear");
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(appUserRepository.save(user)).thenReturn(user);
        String redirectUrl = adminUserController.updateUser(userId, updatedUser);
        verify(appUserRepository).findById(userId);
        verify(appUserRepository).save(user);
        assertEquals(updatedUser.getFirstName(), user.getFirstName());
        assertEquals(updatedUser.getLastName(), user.getLastName());
        assertEquals("redirect:/admin/users/" + userId, redirectUrl);
    }
    /**
     * Testet die Methode zum Aktualisieren eines Benutzers anhand einer ungültigen Benutzer-ID.
     */
    @Test
    public void testUpdateUserWithInvalidId()
    {
        Long userId = 123L;
        AppUser updatedUser = new AppUser();
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());
        String redirectUrl = adminUserController.updateUser(userId, updatedUser);
        verify(appUserRepository).findById(userId);
        assertEquals("redirect:/admin/users/" + userId, redirectUrl);
    }
    /**
     * Testet die Methode zum Anzeigen der Kundenliste.
     */
    @Test
    public void testZeigKunden()
    {
        List<AppUser> users = new ArrayList<>();
        users.add(new AppUser());
        users.add(new AppUser());

        when(appUserRepository.findAll()).thenReturn(users);
        String viewName = adminUserController.zeigKunden(model);
        verify(appUserRepository).findAll();
        verify(model).addAttribute("users", users);
        assertEquals("kunden", viewName);
    }
    /**
     * Testet die Methode zum Stornieren einer Reservierung.
     */
    @Test
    public void testCancelReservation()
    {
        Long userId = 123L;
        Long reservationId = 456L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        String redirectUrl = adminUserController.cancelReservation(userId, reservationId);
        verify(reservationRepository).findById(reservationId);
        verify(reservationRepository).save(reservation);
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        assertEquals("redirect:/admin/users/" + userId + "/kunden-profile", redirectUrl);
    }
    /**
     * Testet die Methode zum Abrufen der Logout-Meldung.
     */
    @Test
    public void testGetLogout()
    {
        String expectedLogoutMessage = "Logout successful.";
        when(ecoService.getLogout()).thenReturn(expectedLogoutMessage);
        String logoutMessage = adminUserController.getLogout();
        assertEquals(expectedLogoutMessage, logoutMessage);
    }
}
