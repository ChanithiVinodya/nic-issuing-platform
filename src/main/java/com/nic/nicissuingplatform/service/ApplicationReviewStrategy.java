package com.nic.nicissuingplatform.service;

import com.nic.nicissuingplatform.entity.Application;
import com.nic.nicissuingplatform.entity.Status;
import com.nic.nicissuingplatform.entity.User;

public interface ApplicationReviewStrategy {
    void review(Application application, Status status, User user);
}
