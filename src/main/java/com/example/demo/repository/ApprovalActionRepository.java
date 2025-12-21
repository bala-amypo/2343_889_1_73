package com.example.demo.repository;

import com.example.demo.model.ApprovalAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalActionRepository
        extends JpaRepository<ApprovalAction, Long> {

    Optional<ApprovalAction> findByLevelAndAction(
            Integer level,
            String action
    );
}
