package com.nic.nicissuingplatform.service;

import com.nic.nicissuingplatform.entity.Application;
import com.nic.nicissuingplatform.entity.Status;
import com.nic.nicissuingplatform.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("adminReviewStrategy")
public class AdminReviewStrategy implements ApplicationReviewStrategy {
    @Autowired
    private ApplicationService applicationService;

    @Override
    public void review(Application application, Status status, User user) {
        // Example: Only allow review if the user is assigned as admin
        if (!user.equals(application.getAssignedAdmin())) {
            throw new SecurityException("Admins can only review their assigned applications.");
        }
        applicationService.updateStatus(application.getId(), status, user);
    }
}
