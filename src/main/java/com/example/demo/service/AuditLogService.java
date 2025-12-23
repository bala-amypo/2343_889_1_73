package com.example.demo.service;

import com.example.demo.model.AuditLogRecord;
import com.example.demo.repository.AuditLogRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AuditLogService {
    private final AuditLogRecordRepository auditLogRecordRepository;

    public AuditLogService(AuditLogRecordRepository auditLogRecordRepository) {
        this.auditLogRecordRepository = auditLogRecordRepository;
    }

    public AuditLogRecord createLog(Long requestId, String eventType, String details) {
        AuditLogRecord log = new AuditLogRecord();
        log.setRequestId(requestId);
        log.setEventType(eventType);
        log.setDetails(details);
        log.setLoggedAt(LocalDateTime.now());
        return auditLogRecordRepository.save(log);
    }

    public List<AuditLogRecord> getLogsByRequestId(Long requestId) {
        return auditLogRecordRepository.findByRequestId(requestId);
    }
}