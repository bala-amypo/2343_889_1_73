package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ApprovalRequest;
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest,Long>{
    
}