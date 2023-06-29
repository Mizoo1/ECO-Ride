package com.ECO.login_System.registration;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        // Regex to validate email
        return true;
    }
}