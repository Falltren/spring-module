package com.fallt.jwt.service;

import com.fallt.jwt.domain.entity.AuditLog;
import com.fallt.jwt.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void saveLog(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
}
