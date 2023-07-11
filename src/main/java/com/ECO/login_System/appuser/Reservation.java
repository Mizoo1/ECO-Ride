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

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private boolean barrierefrei;

    private String fahrzeug;
    private String marke;
    private String motoradMarke;
    private String modelPkw;
    private String modelLkw;
    private String modelMotorad;
    private String anzahlDerTuren;
    private String sprit;
    private String lkwSize;
    private String motoradSize;
    private String anhaengerSize;
    private String zeit;
    private String datum;
    private String abholort;
    private String ort;

}
