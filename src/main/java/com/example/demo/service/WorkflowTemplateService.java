package com.example.demo.service;

import com.example.demo.model.WorkflowTemplate;
import com.example.demo.repository.WorkflowTemplateRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WorkflowTemplateService {
    private final WorkflowTemplateRepository workflowTemplateRepository;

    public WorkflowTemplateService(WorkflowTemplateRepository workflowTemplateRepository) {
        this.workflowTemplateRepository = workflowTemplateRepository;
    }

    public WorkflowTemplate createTemplate(WorkflowTemplate template) {
        if (template.getActive() == null) {
            template.setActive(true);
        }
        return workflowTemplateRepository.save(template);
    }

    public Optional<WorkflowTemplate> getTemplateById(Long id) {
        return workflowTemplateRepository.findById(id);
    }

    public List<WorkflowTemplate> getAllTemplates() {
        return workflowTemplateRepository.findAll();
    }

    public WorkflowTemplate updateTemplate(Long id, WorkflowTemplate template) {
        WorkflowTemplate existing = workflowTemplateRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Template not found"));
        existing.setTemplateName(template.getTemplateName());
        existing.setDescription(template.getDescription());
        existing.setTotalLevels(template.getTotalLevels());
        if (template.getActive() != null) {
            existing.setActive(template.getActive());
        }
        return workflowTemplateRepository.save(existing);
    }

    public WorkflowTemplate activateTemplate(Long id, boolean active) {
        WorkflowTemplate template = workflowTemplateRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Template not found"));
        template.setActive(active);
        return workflowTemplateRepository.save(template);
    }

    public void deleteTemplate(Long id) {
        workflowTemplateRepository.deleteById(id);
    }
}