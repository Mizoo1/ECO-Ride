package com.ECO.login_System.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler
{
    /**
     * Behandelt den erfolgreichen Logout-Vorgang.
     *
     * @param request        Das HttpServletRequest-Objekt.
     * @param response       Das HttpServletResponse-Objekt.
     * @param authentication Die Authentifizierungsinformationen.
     * @throws IOException      Bei Fehlern beim Weiterleiten der Anfrage.
     * @throws ServletException Bei Fehlern während der Verarbeitung der Anfrage.
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException, IOException {
        response.sendRedirect("/login?logout");
    }
}
