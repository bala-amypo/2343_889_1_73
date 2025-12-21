package com.example.demo.service.impl;

import com.example.demo.model.WorkflowTemplate;
import com.example.demo.repository.WorkflowTemplateRepository;
import com.example.demo.service.WorkflowTemplateService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkflowTemplateServiceImpl implements WorkflowTemplateService {

    private final WorkflowTemplateRepository repository;

    public WorkflowTemplateServiceImpl(WorkflowTemplateRepository repository) {
        this.repository = repository;
    }

    public WorkflowTemplate createTemplate(WorkflowTemplate template) {
        return repository.save(template);
    }

    public Optional<WorkflowTemplate> getTemplateById(Long id) {
        return repository.findById(id);
    }

    public List<WorkflowTemplate> getAllTemplates() {
        return repository.findAll();
    }

    public WorkflowTemplate activateTemplate(Long id, boolean active) {
        WorkflowTemplate template = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));
        template.setActive(active);
        return repository.save(template);
    }
}
