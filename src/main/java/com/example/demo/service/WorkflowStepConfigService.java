package com.example.demo.service;

import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.repository.WorkflowStepConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class WorkflowStepConfigService {
    private final WorkflowStepConfigRepository workflowStepConfigRepository;

    public WorkflowStepConfigService(WorkflowStepConfigRepository workflowStepConfigRepository) {
        this.workflowStepConfigRepository = workflowStepConfigRepository;
    }

    public WorkflowStepConfig createStep(WorkflowStepConfig step) {
        return workflowStepConfigRepository.save(step);
    }

    public List<WorkflowStepConfig> getStepsForTemplate(Long templateId) {
        return workflowStepConfigRepository.findByTemplateIdOrderByLevelNumberAsc(templateId);
    }
}