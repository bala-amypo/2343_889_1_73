package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ApprovalAction;
public interface ApprovalActionRepository extends JpaRepository<ApprovalAction,Long>{
    
}