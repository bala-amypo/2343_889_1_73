package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.WorkflowStepConfig;
public interface WorkflowStepConfigRepository extends JpaRepository<WorkflowStepConfig,Long>{
    
}