package com.nic.nicissuingplatform.service;

import com.nic.nicissuingplatform.entity.ActionLog;
import com.nic.nicissuingplatform.entity.Application;
import com.nic.nicissuingplatform.entity.Status;
import com.nic.nicissuingplatform.entity.User;
import com.nic.nicissuingplatform.repository.ActionLogRepository;
import com.nic.nicissuingplatform.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ActionLogRepository actionLogRepository;

    @Autowired
    private UserService userService;

    public List<Application> getUnassignedApplications() {
        return applicationRepository.findByAssignedAdminIsNull();
    }

    public List<Application> getAssignedApplications(User admin) {
        return applicationRepository.findByAssignedAdmin(admin);
    }
    // ✓ Checks if application exists
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));
    }
    @Transactional
    public void assignApplication(Long appId, Long adminId) {
        Application app = getApplicationById(appId);
        User admin = userService.getUserById(adminId);

        if (app.getAssignedAdmin() != null) {
            throw new IllegalStateException("Application is already assigned");
        }
        app.setAssignedAdmin(admin);
        applicationRepository.save(app);
        logAction("Assigned application " + appId + " to admin " + adminId, admin, app);
    }

    public void updateStatus(Long appId, Status status, User user) {
        Application app = getApplicationById(appId);
        app.setStatus(status);
        applicationRepository.save(app);
        logAction("Updated status to " + status + " for application " + appId, user, app);
    }

    public void deleteApplication(Long appId, User user) {
        Application app = getApplicationById(appId);

        //  Log first while the application still exists
        logAction("Deleted application " + appId, user, app);

        //  Then delete it
        applicationRepository.delete(app);
    }


    private void logAction(String action, User user, Application app) {
        ActionLog log = new ActionLog();
        log.setAction(action);
        log.setUser(user);
        log.setTimestamp(LocalDateTime.now());
       // log.setApplication(app);
        log.setApplicationId(app.getId());
        actionLogRepository.save(log);
    }
}
