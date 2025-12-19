package com.example.demo.repository;

import com.example.demo.model.WorkflowStepConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowStepConfigRepository
        extends JpaRepository<WorkflowStepConfig, Long> {

    List<WorkflowStepConfig> findByTemplateIdOrderByLevelNumberAsc(Long templateId);
}
