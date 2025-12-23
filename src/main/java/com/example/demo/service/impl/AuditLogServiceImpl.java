package com.example.demo.service.impl;

import com.example.demo.model.AuditLogRecord;
import com.example.demo.repository.AuditLogRecordRepository;
import com.example.demo.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRecordRepository auditLogRecordRepository;

    public AuditLogServiceImpl(AuditLogRecordRepository auditLogRecordRepository) {
        this.auditLogRecordRepository = auditLogRecordRepository;
    }

    @Override
    public AuditLogRecord logEvent(Long requestId, String eventType, String details) {
        AuditLogRecord record = new AuditLogRecord();
        record.setRequestId(requestId);
        record.setEventType(eventType);
        record.setDetails(details);
        record.setLoggedAt(LocalDateTime.now());
        return auditLogRecordRepository.save(record);
    }

    @Override
    public List<AuditLogRecord> getLogsByRequestId(Long requestId) {
        return auditLogRecordRepository.findByRequestId(requestId);
    }
}
