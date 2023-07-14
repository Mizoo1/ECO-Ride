package com.ECO.login_System.security.config;

import com.ECO.login_System.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    /**
     * Konfiguriert die Sicherheitseinstellungen für HTTP-Anfragen.
     *
     * @param http Der HttpSecurity-Objekt für die Konfiguration.
     * @throws Exception Bei Fehlern während der Konfiguration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**", "/contact", "/reservierung", "/index")
                .permitAll()
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/users").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }
    @GetMapping("/access-denied")
    public String showAccessDeniedPage()
    {
        return "access-denied";
    }
    /**
     * Konfiguriert das Ignorieren bestimmter Ressourcen.
     *
     * @param web Das WebSecurity-Objekt für die Konfiguration.
     * @throws Exception Bei Fehlern während der Konfiguration.
     */
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/img/**",
                "/js/**", "/api/v*/registration/**",
                "contact","reservierung","index");
    }
    /**
     * Konfiguriert den AuthenticationManagerBuilder mit dem AuthenticationProvider.
     *
     * @param auth Der AuthenticationManagerBuilder.
     * @throws Exception Bei Fehlern während der Konfiguration.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    /**
     * Erstellt den DaoAuthenticationProvider.
     *
     * @return Der DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}
