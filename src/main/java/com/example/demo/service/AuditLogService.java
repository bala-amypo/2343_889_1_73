package com.example.demo.service;

import com.example.demo.model.AuditLogRecord;
import com.example.demo.repository.AuditLogRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRecordRepository auditLogRepository;

    public AuditLogService(AuditLogRecordRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public AuditLogRecord logEvent(Long requestId, String eventType, String details) {
        AuditLogRecord log = new AuditLogRecord(requestId, eventType, details);
        return auditLogRepository.save(log);
    }

    public List<AuditLogRecord> getLogsByRequestId(Long requestId) {
        return auditLogRepository.findByRequestId(requestId);
    }

    public List<AuditLogRecord> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
