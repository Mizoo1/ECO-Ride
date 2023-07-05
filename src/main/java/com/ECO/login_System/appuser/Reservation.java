package com.ECO.login_System.appuser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Reservation {

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auto;
    private String zeit;
    private String datum;
    private String abholort;
    private String ort;


    // Constructors, getters, and setters

}
