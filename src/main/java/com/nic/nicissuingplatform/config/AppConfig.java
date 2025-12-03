package com.nic.nicissuingplatform.config;

import com.nic.nicissuingplatform.service.AdminReviewStrategy;
import com.nic.nicissuingplatform.service.SuperAdminReviewStrategy;
import com.nic.nicissuingplatform.service.ApplicationReviewStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean("adminReviewStrategy")
    public ApplicationReviewStrategy adminReviewStrategy() {
        return new AdminReviewStrategy();
    }
    @Bean("superAdminReviewStrategy")
    public ApplicationReviewStrategy superAdminReviewStrategy() {
        return new SuperAdminReviewStrategy();
    }*/
}