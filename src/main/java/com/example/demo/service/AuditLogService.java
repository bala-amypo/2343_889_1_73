package com.example.demo.service;

import com.example.demo.model.AuditLogRecord;
import java.util.List;

public interface AuditLogService {
    AuditLogRecord logEvent(Long requestId, String eventType, String details);
    List<AuditLogRecord> getLogsByRequestId(Long requestId);
    List<AuditLogRecord> getAllLogs();
}