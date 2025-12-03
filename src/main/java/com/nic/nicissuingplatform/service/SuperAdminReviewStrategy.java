package com.nic.nicissuingplatform.service;

import com.nic.nicissuingplatform.entity.Application;
import com.nic.nicissuingplatform.entity.Status;
import com.nic.nicissuingplatform.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("superAdminReviewStrategy")
public class SuperAdminReviewStrategy implements ApplicationReviewStrategy {
    @Autowired
    private ApplicationService applicationService;

    @Override
    public void review(Application application, Status status, User user) {
        // SuperAdmin can review any application
        applicationService.updateStatus(application.getId(), status, user);
    }
}
