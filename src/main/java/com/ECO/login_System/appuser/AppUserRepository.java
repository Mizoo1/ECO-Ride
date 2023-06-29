package com.ECO.login_System.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AppUserRepository
                extends JpaRepository<AppUser, Long> {

        Optional<AppUser> findByEmail(String email);

        @Transactional
        @Modifying
        @Query("UPDATE AppUser a " +
                        "SET a.enabled = TRUE WHERE a.email = ?1")
        int enableAppUser(String email);

}