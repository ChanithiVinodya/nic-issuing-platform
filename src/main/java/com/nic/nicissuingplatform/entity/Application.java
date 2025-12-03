package com.nic.nicissuingplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "application")
@Data
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Applicant details (expand as needed)
    private String applicantName;
    private String applicantDetails; // JSON or fields for docs, etc.
    private String documents; // Path or JSON for uploaded docs

    @Enumerated(EnumType.STRING)
//    private String status;
    private Status status = Status.PENDING;

    @ManyToOne
    @JoinColumn(name = "assigned_admin_id")
    private User assignedAdmin;
}

