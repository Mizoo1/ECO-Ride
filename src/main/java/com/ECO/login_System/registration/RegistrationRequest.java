package com.ECO.login_System.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String titel;
    private final String adresse;
    private final String plz;
    private final String stadt;
    private final String telefonnummer;
    private final String geburtsdatum;
    private final String geburtsort;
    private final String fuehrerscheinnummer;
    private final String erteilungsdatum;
    private final String ablaufdatum;
    private final String ausstellungsort;
    private final String personalausweisnummer;
    private final String reisepassnummer;
    private final String tarif;
    private final String payMethod;

    // Konstruktor, Getter und andere Methoden
}
