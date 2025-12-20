package com.example.demo.service;

import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.repository.WorkflowStepConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkflowStepConfigService {

    private final WorkflowStepConfigRepository stepConfigRepository;

    public WorkflowStepConfigService(WorkflowStepConfigRepository stepConfigRepository) {
        this.stepConfigRepository = stepConfigRepository;
    }

    @Transactional
    public WorkflowStepConfig createStep(WorkflowStepConfig stepConfig) {
        return stepConfigRepository.save(stepConfig);
    }

    public List<WorkflowStepConfig> getStepsForTemplate(Long templateId) {
        return stepConfigRepository.findByTemplateIdOrderByLevelNumberAsc(templateId);
    }
}