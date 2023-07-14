package com.ECO.controller;

import com.ECO.login_System.registration.RegistrationService;
import com.ECO.service.ContactService;
import com.ECO.service.EcoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac)
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    /**Test prüft, ob das Senden eines POST-Anforderung
     * an die URL "/api/v/registration/submit_form"
     * ohne Anmeldung richtig umgeleitet wird.
     * */
    @Test
    public void submitFormWithoutLoginTest() throws Exception
    {
        mockMvc.perform(post("/api/v/registration/submit_form")
                        .param("name", "Muaaz")
                        .param("vorname", "Bdear")
                        .param("message", "Ich mache ein Test mit Junit!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/v/registration/contact?success"));
    }
    /**Test prüft, ob das Senden eines POST-Anforderung
     * an die URL "/submit_form" mit Anmeldung richtig umgeleitet wird.
     * */
    @Test
    public void submitFormWithLoginTest() throws Exception
    {
        mockMvc.perform(post("/submit_form")
                        .param("id", "100")
                        .param("name", "Muaaz")
                        .param("vorname", "Bdear")
                        .param("message", "Ich mache ein Test mit Junit!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact?success"));
    }
}
