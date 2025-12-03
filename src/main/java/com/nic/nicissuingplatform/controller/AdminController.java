package com.nic.nicissuingplatform.controller;

import com.nic.nicissuingplatform.entity.Application;
import com.nic.nicissuingplatform.entity.Status;
import com.nic.nicissuingplatform.entity.User;
import com.nic.nicissuingplatform.service.ApplicationReviewStrategy;
import com.nic.nicissuingplatform.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    @Qualifier("adminReviewStrategy")
    private ApplicationReviewStrategy reviewStrategy;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Application> apps = applicationService.getAssignedApplications(user);
        model.addAttribute("apps", apps);
        return "admin-dashboard";
    }

    @GetMapping("/review/{id}")
    public String review(@PathVariable Long id, Model model, @RequestParam(value = "deleted", required = false) String deleted) {
        try {
            Application app = applicationService.getApplicationById(id);
            model.addAttribute("app", app);
            if (deleted != null) {
                model.addAttribute("deleted", true);
            }
            return "review-application";
        } catch (RuntimeException e) {
            return "redirect:/admin/delete-success";
        }
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long appId, @RequestParam Status status, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Application app = applicationService.getApplicationById(appId);
        reviewStrategy.review(app, status, user);
        return "redirect:/admin/dashboard";
    }

    /*
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        applicationService.deleteApplication(id, user);
        return "redirect:/admin/delete-success";
    }*/

    @GetMapping("/delete/{id}")
    public String deleteApplication(@PathVariable Long id, Authentication authentication, Model model) {
        try {
            User user = (User) authentication.getPrincipal();
            applicationService.deleteApplication(id, user);
            return "redirect:/admin/delete-success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to delete application: " + e.getMessage());
            return "error";
        }
    }


    @GetMapping("/delete-success")
    public String deleteSuccess() {
        return "delete-success";
    }
}
