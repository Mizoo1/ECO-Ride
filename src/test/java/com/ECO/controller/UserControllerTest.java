package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.AppUserRepository;
import com.ECO.login_System.appuser.AppUserService;
import com.ECO.login_System.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest
{
    @InjectMocks
    UserController userController;
    @Mock
    AppUserService appUserService;
    @Mock
    AppUserRepository appUserRepository;
    @Mock
    RegistrationService registrationService;
    @BeforeEach
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateUser()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("User-Agent", "testUserAgent");
        AppUser user = new AppUser();
        Long id = 1L;
        when(registrationService.getOperatingSystemFromUserAgent("testUserAgent")).thenReturn("testOS");
        when(appUserService.updateAppUser(id, user)).thenReturn(user);
        String response = userController.updateUser(id, user, request);
        assertEquals("redirect:/user/1", response);
    }

    @Test
    public void testDeleteUser()
    {
        Long id = 1L;
        AppUser user = new AppUser();
        when(appUserRepository.findById(id)).thenReturn(Optional.of(user));
        ResponseEntity<String> response = userController.deleteUser(id);
        assertEquals("User deleted successfully", response.getBody());
    }
}
