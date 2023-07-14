package com.ECO.login_System.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    /**
     * Behandelt den erfolgreichen Authentifizierungsversuch.
     *
     * @param request        Das HttpServletRequest-Objekt.
     * @param response       Das HttpServletResponse-Objekt.
     * @param authentication Die Authentifizierungsinformationen.
     * @throws IOException      Bei Fehlern beim Weiterleiten der Anfrage.
     * @throws ServletException Bei Fehlern während der Verarbeitung der Anfrage.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        response.sendRedirect("/index");
    }
}
