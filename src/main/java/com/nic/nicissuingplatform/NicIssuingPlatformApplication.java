package com.nic.nicissuingplatform;

import com.nic.nicissuingplatform.entity.User;
import com.nic.nicissuingplatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class NicIssuingPlatformApplication {

    private static final Logger logger = LoggerFactory.getLogger(NicIssuingPlatformApplication.class);

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(NicIssuingPlatformApplication.class, args);
    }

    @PostConstruct
    public void displayUserDetails() {
        logger.info("Fetching all users from the database...");
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                logger.info("No users found in the database.");
            } else {
                for (User user : users) {
                    logger.info("User ID: {}, Username: {}, Password: {}, Role: {}",
                            user.getId(), user.getUsername(), user.getPassword(), user.getRole());
                }
            }
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage());
        }
    }

}