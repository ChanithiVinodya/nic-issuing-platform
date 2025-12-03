package com.nic.nicissuingplatform.controller;

import com.nic.nicissuingplatform.entity.Application;
import com.nic.nicissuingplatform.entity.Status;
import com.nic.nicissuingplatform.entity.User;
import com.nic.nicissuingplatform.service.ApplicationReviewStrategy;
import com.nic.nicissuingplatform.service.ApplicationService;
import com.nic.nicissuingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("superAdminReviewStrategy")
    private ApplicationReviewStrategy reviewStrategy;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Application> pendingApps = applicationService.getUnassignedApplications();
        List<User> admins = userService.getAllAdmins();
        model.addAttribute("pendingApps", pendingApps);
        model.addAttribute("admins", admins);
        return "superadmin-dashboard";
    }

    @PostMapping("/assign")
    public String assign(@RequestParam Long appId, @RequestParam Long adminId) {
        applicationService.assignApplication(appId, adminId);
        return "redirect:/superadmin/dashboard";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long appId, @RequestParam Status status, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Application app = applicationService.getApplicationById(appId);
        reviewStrategy.review(app, status, user);
        return "redirect:/superadmin/dashboard";
    }

    @PostMapping("/create-admin")
    public String createAdmin(@RequestParam String username, @RequestParam String password) {
        userService.createAdmin(username, password);
        return "redirect:/superadmin/dashboard";
    }

    @GetMapping("/delete-admin/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        userService.deleteAdmin(id);
        return "redirect:/superadmin/dashboard";
    }
}
