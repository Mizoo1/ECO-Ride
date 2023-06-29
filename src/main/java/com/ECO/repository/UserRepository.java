package com.ECO.repository;

import com.ECO.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRole(String role);

    List<User> findByFirstName(String firstName);
}
