package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.service.EcoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class HomeControllerTest
{
    @MockBean
    private EcoService ecoService;

    @Autowired
    private HomeController homeController;
    @MockBean
    private Authentication authentication;

    /**
     * Testet die {@code getHomeWithoutLogin} Methode des {@code homeController} Objekts.
     * Überprüft, ob der erwartete View-Name zurückgegeben wird, wenn kein Benutzer angemeldet ist.
     */
    @Test
    public void testGetHomeWithoutLogin()
    {
        when(ecoService.getHomeWithoutLogin()).thenReturn("indexWithoutLogin");
        String response = homeController.getHomeWithoutLogin();
        assertEquals("indexWithoutLogin", response);
    }
    /**
     * Testet die {@code getHome} Methode des {@code homeController} Objekts.
     * Überprüft, ob das erwartete ModelAndView-Objekt zurückgegeben wird und
     * ob der Benutzername korrekt gesetzt ist.
     */
    @Test
    public void testGetHome()
    {
        AppUser mockUser = new AppUser();
        mockUser.setUserName("TestUser");
        when(authentication.getPrincipal()).thenReturn(mockUser);
        ModelAndView expectedModelAndView = new ModelAndView("index");
        expectedModelAndView.addObject("userName", mockUser.getUserName());
        when(ecoService.getHome(authentication)).thenReturn(expectedModelAndView);
        ModelAndView response = homeController.getHome(authentication);
        assertEquals(expectedModelAndView.getViewName(), response.getViewName());
        assertEquals(expectedModelAndView.getModel().
                get("userName"), response.getModel().get("userName"));
    }
    /**
     * Testet die {@code getRegister} Methode des {@code homeController} Objekts.
     * Überprüft, ob der erwartete View-Name für die Registrierungsseite zurückgegeben wird.
     */
    @Test
    public void testGetRegister()
    {
        when(ecoService.getRegister()).thenReturn("register");
        String response = homeController.getRegister();
        assertEquals("register", response);
    }
    /**
     * Testet die {@code getRegisterAdmin} Methode des {@code homeController} Objekts.
     * Überprüft, ob der erwartete View-Name für die Registrierungsseite
     * des Administrators zurückgegeben wird.
     */
    @Test
    public void testGetRegisterAdmin()
    {
        when(ecoService.getRegisterAdmin()).thenReturn("registerAdmin");
        String response = homeController.getRegisterAdmin();
        assertEquals("registerAdmin", response);
    }
    /**
     * Testet die {@code zeigeProfil} Methode des {@code homeController} Objekts.
     * Überprüft, ob das erwartete ModelAndView-Objekt zurückgegeben
     * wird und ob der Benutzername und die Benutzerdetails korrekt gesetzt sind.
     */
    @Test
    public void testZeigeProfil()
    {
        AppUser mockUser = new AppUser();
        mockUser.setUserName("TestUser");
        when(authentication.getPrincipal()).thenReturn(mockUser);
        ModelAndView expectedModelAndView = new ModelAndView("profile");
        expectedModelAndView.addObject("userName", mockUser.getUserName());
        expectedModelAndView.addObject("userDetails", mockUser);
        when(ecoService.zeigeProfil(authentication)).thenReturn(expectedModelAndView);
        ModelAndView response = homeController.zeigeProfil(authentication);
        assertEquals(expectedModelAndView.getViewName(), response.getViewName());
        assertEquals(expectedModelAndView.getModel().
                get("userName"), response.getModel().get("userName"));
        assertEquals(expectedModelAndView.getModel().
                get("userDetails"), response.getModel().get("userDetails"));
    }
    /**
     * Testet die {@code getContact} Methode des {@code homeController} Objekts.
     * Überprüft, ob das erwartete ModelAndView-Objekt zurückgegeben
     * wird und ob der Benutzername und die Benutzerdetails korrekt gesetzt sind.
     */
    @Test
    public void testGetContact()
    {
        AppUser mockUser = new AppUser();
        mockUser.setUserName("TestUser");
        when(authentication.getPrincipal()).thenReturn(mockUser);
        ModelAndView expectedModelAndView = new ModelAndView("contact");
        expectedModelAndView.addObject("userName", mockUser.getUserName());
        expectedModelAndView.addObject("userDetails", mockUser);
        when(ecoService.getContact(authentication)).thenReturn(expectedModelAndView);
        ModelAndView response = homeController.getContact(authentication);
        assertEquals(expectedModelAndView.getViewName(), response.getViewName());
        assertEquals(expectedModelAndView.getModel().
                get("userName"), response.getModel().get("userName"));
        assertEquals(expectedModelAndView.getModel().
                get("userDetails"), response.getModel().get("userDetails"));
    }
    /**
     * Testet die getServices-Methode des HomeController.
     * Stellt sicher, dass die Methode den erwarteten String zurückgibt,
     * wenn ein Nutzer nicht angemeldet ist.
     */
    @Test
    public void testGetServices()
    {
        when(ecoService.getServicesWithoutLogin()).thenReturn("serviceWithoutLogin");
        String response = homeController.getServices();
        assertEquals("serviceWithoutLogin", response);
    }
    /**
     * Testet die getServicesWithoutLogin-Methode des HomeController.
     * Stellt sicher, dass die Methode ein ModelAndView-Objekt mit
     * den richtigen Daten zurückgibt.
     */
    @Test
    public void testGetServicesWithoutLogin()
    {
        AppUser mockUser = new AppUser();
        mockUser.setUserName("TestUser");
        when(authentication.getPrincipal()).thenReturn(mockUser);
        ModelAndView expectedModelAndView = new ModelAndView("services");
        expectedModelAndView.addObject("userName", mockUser.getUserName());
        when(ecoService.getServices(authentication)).thenReturn(expectedModelAndView);
        ModelAndView response = homeController.getServicesWithoutLogin(authentication);
        assertEquals(expectedModelAndView.getViewName(), response.getViewName());
        assertEquals(expectedModelAndView.getModel().get("userName"), response.getModel().get("userName"));
    }
    /**
     * Testet die getAbout-Methode des HomeController.
     * Stellt sicher, dass die Methode den erwarteten String zurückgibt,
     * wenn ein Nutzer nicht angemeldet ist.
     */
    @Test
    public void testGetAbout()
    {
        when(ecoService.getAboutWithoutLogin()).thenReturn("aboutWithoutLogin");
        String response = homeController.getAbout();
        assertEquals("aboutWithoutLogin", response);
    }
}