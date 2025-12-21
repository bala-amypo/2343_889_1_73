package com.example.demo.repository;

import com.example.demo.model.ApprovalAction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApprovalActionRepository
        extends JpaRepository<ApprovalAction, Long> {

    List<ApprovalAction> findByLevelNumberAndAction(Integer levelNumber, String action);
}
