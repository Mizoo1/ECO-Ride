package com.ECO.login_System.appuser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Reservation {

        @ManyToOne
        @JoinColumn(name = "app_user_id")
        private AppUser appUser;

        private String auto;
    private String zeit;
    private String datum;
    private String abholort;
    private String ort;
    @Id
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}