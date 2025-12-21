package com.example.demo.repository;

import com.example.demo.model.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApprovalRequestRepository
        extends JpaRepository<ApprovalRequest, Long> {

    List<ApprovalRequest> findByRequesterId(Long requesterId);
}
