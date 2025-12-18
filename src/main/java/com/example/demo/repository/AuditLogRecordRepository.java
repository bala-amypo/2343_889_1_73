package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.AuditLogRecord;
public interface AuditLogRecordRepository extends JpaRepository<AuditLogRecord,Long>{
    
}