package com.nic.nicissuingplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ActionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /*
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;  */

    //added to avoid cyclic dependency during serialization
    @Column(name = "application_id")
    private Long applicationId;





}
