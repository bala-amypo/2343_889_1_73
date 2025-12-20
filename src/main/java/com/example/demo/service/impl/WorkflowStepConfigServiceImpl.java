package com.example.demo.service.impl;

import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.repository.WorkflowStepConfigRepository;
import com.example.demo.service.WorkflowStepConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkflowStepConfigServiceImpl implements WorkflowStepConfigService {

    private final WorkflowStepConfigRepository stepConfigRepository;

    public WorkflowStepConfigServiceImpl(WorkflowStepConfigRepository stepConfigRepository) {
        this.stepConfigRepository = stepConfigRepository;
    }

    @Override
    @Transactional
    public WorkflowStepConfig createStep(WorkflowStepConfig stepConfig) {
        return stepConfigRepository.save(stepConfig);
    }

    @Override
    public List<WorkflowStepConfig> getStepsForTemplate(Long templateId) {
        return stepConfigRepository.findByTemplateIdOrderByLevelNumberAsc(templateId);
    }
}
