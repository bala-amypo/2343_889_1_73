package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ApprovalAction;
import java.util.List;

public interface ApprovalActionRepository extends JpaRepository<ApprovalAction, Long> {

    List<ApprovalAction> findByLevelAndAction(Integer levelNumber, String action);
}
