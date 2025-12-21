package com.example.demo.service;

import com.example.demo.model.AuditLogRecord;

public interface AuditLogService {

    AuditLogRecord saveLog(AuditLogRecord log);
}
