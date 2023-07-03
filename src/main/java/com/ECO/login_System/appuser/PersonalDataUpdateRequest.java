package com.ECO.login_System.appuser;

import java.time.LocalDate;

public class PersonalDataUpdateRequest {
    private String firstName;
    private String lastName;
    private String geburtsdatum;
    private String geburtsort;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getGeburtsort() {
        return geburtsort;
    }
}
