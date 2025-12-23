package com.example.demo.repository;

import com.example.demo.model.AuditLogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRecordRepository extends JpaRepository<AuditLogRecord, Long> {
    List<AuditLogRecord> findByRequestId(Long requestId);
}