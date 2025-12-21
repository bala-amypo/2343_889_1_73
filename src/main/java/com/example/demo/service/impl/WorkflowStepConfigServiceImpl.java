package com.example.demo.service.impl;

import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.repository.WorkflowStepConfigRepository;
import com.example.demo.service.WorkflowStepConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowStepConfigServiceImpl implements WorkflowStepConfigService {

    private final WorkflowStepConfigRepository repository;

    public WorkflowStepConfigServiceImpl(WorkflowStepConfigRepository repository) {
        this.repository = repository;
    }

    public WorkflowStepConfig createStep(WorkflowStepConfig step) {
        return repository.save(step);
    }

    public List<WorkflowStepConfig> getStepsForTemplate(Long templateId) {
        return repository.findByTemplateIdOrderByLevelNumberAsc(templateId);
    }
}
