package com.ECO.login_System.appuser;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {


    @SequenceGenerator(
            name = "Eco_sequence",
            sequenceName = "Eco_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Eco_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String titel;
    private String adresse;
    private String plz;
    private String stadt;

    private String telefonnummer;
    private String mobilnummer;
    private String geburtsdatum;
    private String geburtsort;
    private String fuehrerscheinnumme;
    private String erteilungsdatum;
    private String ablaufdatum;
    private String ausstellungsort;
    private String personalausweisnummer;
    private String reisepassnummer;

    private String tarif;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked = false;
    private Boolean enabled = false;



    public AppUser(String firstName, String lastName,
                   String email, String password,
                   String titel, String adresse, String plz,
                   String stadt,  String telefonnummer,
                   String geburtsdatum,
                   String geburtsort,
                   String fuehrerscheinnummer, String erteilungsdatum,
                   String ablaufdatum, String ausstellungsort,
                   String personalausweisnummer,
                   String reisepassnummer,
                   String tarif,AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.titel = titel;
        this.password = password;
        this.adresse = adresse;
        this.plz = plz;
        this.stadt = stadt;
        this.telefonnummer = telefonnummer;
        this.geburtsdatum = geburtsdatum;
        this.geburtsort = geburtsort;
        this.tarif = tarif;
        this.fuehrerscheinnumme = fuehrerscheinnummer;
        this.erteilungsdatum = erteilungsdatum;
        this.ablaufdatum = ablaufdatum;
        this.ausstellungsort = ausstellungsort;
        this.personalausweisnummer = personalausweisnummer;
        this.reisepassnummer = reisepassnummer;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return titel;
    }


    public String getAdresse() {
        return adresse;
    }

    public String getPlz() {
        return plz;
    }

    public String getStadt() {
        return stadt;
    }


    public String getTelefonnummer() {
        return telefonnummer;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getGeburtsort() {
        return geburtsort;
    }

    public String getFuehrerscheinnumme() {
        return fuehrerscheinnumme;
    }

    public String getErteilungsdatum() {
        return erteilungsdatum;
    }

    public String getAblaufdatum() {
        return ablaufdatum;
    }

    public String getAusstellungsort() {
        return ausstellungsort;
    }

    public String getPersonalausweisnummer() {
        return personalausweisnummer;
    }

    public String getReisepassnummer() {
        return reisepassnummer;
    }

    public String getTarif() {
        return tarif;
    }

    public AppUserRole getAppUserRole() {
        return appUserRole;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}