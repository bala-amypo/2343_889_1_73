package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.WorkflowTemplate;
import com.example.demo.repository.WorkflowTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkflowTemplateService {

    private final WorkflowTemplateRepository templateRepository;

    public WorkflowTemplateService(WorkflowTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Transactional
    public WorkflowTemplate createTemplate(WorkflowTemplate template) {
        if (template.getTotalLevels() == null || template.getTotalLevels() <= 0) {
            throw new IllegalArgumentException("Total levels must be positive");
        }
        return templateRepository.save(template);
    }

    @Transactional
    public WorkflowTemplate updateTemplate(Long id, WorkflowTemplate template) {
        WorkflowTemplate existing = getTemplateById(id);
        existing.setTemplateName(template.getTemplateName());
        existing.setDescription(template.getDescription());
        if (template.getTotalLevels() != null && template.getTotalLevels() > 0) {
            existing.setTotalLevels(template.getTotalLevels());
        }
        return templateRepository.save(existing);
    }

    public WorkflowTemplate getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + id));
    }

    public List<WorkflowTemplate> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Transactional
    public WorkflowTemplate activateTemplate(Long id) {
        WorkflowTemplate template = getTemplateById(id);
        template.setActive(true);
        return templateRepository.save(template);
    }

    @Transactional
    public WorkflowTemplate deactivateTemplate(Long id) {
        WorkflowTemplate template = getTemplateById(id);
        template.setActive(false);
        return templateRepository.save(template);
    }
}
