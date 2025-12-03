package com.nic.nicissuingplatform.repository;

import com.nic.nicissuingplatform.entity.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
}
