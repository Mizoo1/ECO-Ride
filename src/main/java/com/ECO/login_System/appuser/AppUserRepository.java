package com.ECO.login_System.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    @Query("SELECT DISTINCT u.tarif FROM AppUser u")
    List<String> getAllTarifTypes();

    @Query("SELECT COUNT(u) FROM AppUser u WHERE u.tarif = ?1")
    int getUserCountByTarif(String tarif);

    @Query("SELECT u FROM AppUser u WHERE u.id = ?1 OR u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.email LIKE %?1% OR u.tarif LIKE %?1%")
    List<AppUser> searchUsers(String keyword);

    @Query("SELECT u FROM AppUser u WHERE u.id = ?2 OR LOWER(u.firstName) LIKE %?1% OR LOWER(u.lastName) LIKE %?1% OR LOWER(u.email) LIKE %?1% OR LOWER(u.tarif) LIKE %?1%")
    List<AppUser> searchUsersByCriteria(String keyword, Long id);

    @Query("SELECT u.tarif, COUNT(u.tarif) FROM AppUser u GROUP BY u.tarif")
    List<Object[]> countBookingsByTarif();

    @Query("SELECT u.payMethod, COUNT(u.payMethod) FROM AppUser u GROUP BY u.payMethod")
    List<Object[]> countPaymentMethods();

    @Query("SELECT EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.geburtsdatum), COUNT(u) " +
            "FROM AppUser u " +
            "GROUP BY EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.geburtsdatum)")
    List<Object[]> calculateAgeStatistics();

    @Query("SELECT u.operatingSystem, COUNT(u.operatingSystem) FROM AppUser u GROUP BY u.operatingSystem")
    List<Object[]> countOperatingSystems();


    @Transactional
    @Modifying
    @Query("UPDATE AppUser a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM ConfirmationToken c WHERE c.appUser.id = ?1")
    void deleteConfirmationTokenByUserId(Long userId);

    List<AppUser> findByUserName(String userName);

}
