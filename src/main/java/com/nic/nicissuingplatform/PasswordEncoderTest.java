package com.nic.nicissuingplatform;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "password"; // The password you want to use
        String hash = encoder.encode(password);
        System.out.println("BCrypt hash for 'password': " + hash);
    }
}
