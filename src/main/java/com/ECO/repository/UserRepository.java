package com.ECO.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECO.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findById(long id);

    User save(User user);

    User findByEmail(String email);

    List<User> findByRole(String role);

    List<User> findByFirstName(String firstName);


}




    




