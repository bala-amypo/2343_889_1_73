package com.example.demo.service.impl;

import com.example.demo.model.AuditLogRecord;
import com.example.demo.repository.AuditLogRecordRepository;
import com.example.demo.service.AuditLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRecordRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRecordRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Transactional
    public AuditLogRecord logEvent(Long requestId, String eventType, String details) {
        AuditLogRecord log = new AuditLogRecord(requestId, eventType, details);
        return auditLogRepository.save(log);
    }

    @Override
    public List<AuditLogRecord> getLogsByRequestId(Long requestId) {
        return auditLogRepository.findByRequestId(requestId);
    }

    @Override
    public List<AuditLogRecord> getAllLogs() {
        return auditLogRepository.findAll();
    }
}