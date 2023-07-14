package com.ECO.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LoginController loginController;
    /**
     * Setup-Methode, die vor jedem Test ausgeführt wird. Initialisiert die Mocks und
     * konfiguriert das MockMvc-Objekt.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(viewResolver)
                .build();
    }
    /**
     * Testet die showLogin-Methode des LoginController. Erwartet, dass der Status der
     * Antwort OK ist und dass die Antwortansicht "login" ist.
     */
    @Test
    public void testShowLogin() throws Exception
    {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
    /**
     * Testet die showAdminLogin-Methode des LoginController. Erwartet, dass der Status
     * der Antwort OK ist und dass die Antwortansicht "login-admin" ist.
     */
    @Test
    public void testShowAdminLogin() throws Exception
    {
        mockMvc.perform(get("/admin/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-admin"));
    }

}
