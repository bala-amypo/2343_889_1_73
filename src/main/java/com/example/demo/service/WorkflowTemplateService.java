package com.example.demo.service;

import com.example.demo.model.WorkflowTemplate;
import java.util.List;
import java.util.Optional;

public interface WorkflowTemplateService {

    WorkflowTemplate createTemplate(WorkflowTemplate template);

    Optional<WorkflowTemplate> getTemplateById(Long id);

    List<WorkflowTemplate> getAllTemplates();

    WorkflowTemplate activateTemplate(Long id, boolean active);
}
