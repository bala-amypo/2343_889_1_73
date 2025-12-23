package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.AuditLogRecord;
import java.util.List;

public interface AuditLogRecordRepository extends JpaRepository<AuditLogRecord, Long> {

    List<AuditLogRecord> findByRequestId(Long requestId);
}
