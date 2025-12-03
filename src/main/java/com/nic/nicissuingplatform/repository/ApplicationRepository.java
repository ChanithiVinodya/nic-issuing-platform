package com.nic.nicissuingplatform.repository;

import com.nic.nicissuingplatform.entity.Application;
import com.nic.nicissuingplatform.entity.Status;
import com.nic.nicissuingplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByAssignedAdmin(User user);
    List<Application> findByAssignedAdminIsNull();
    List<Application> findByStatus(Status status);
}
