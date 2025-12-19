package com.example.demo.service;

import com.example.demo.model.WorkflowStepConfig;

import java.util.List;

public interface WorkflowStepConfigService {

    WorkflowStepConfig createStep(WorkflowStepConfig step);

    List<WorkflowStepConfig> getStepsForTemplate(Long templateId);
}
