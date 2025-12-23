package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.WorkflowStepConfig;
import java.util.List;

public interface WorkflowStepConfigRepository extends JpaRepository<WorkflowStepConfig, Long> {

    List<WorkflowStepConfig> findByTemplateIdOrderByLevelNumberAsc(Long templateId);
}
