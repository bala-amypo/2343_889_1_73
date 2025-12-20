package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.WorkflowTemplate;
import com.example.demo.repository.WorkflowTemplateRepository;
import com.example.demo.service.WorkflowTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkflowTemplateServiceImpl implements WorkflowTemplateService {

    private final WorkflowTemplateRepository templateRepository;

    public WorkflowTemplateServiceImpl(WorkflowTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    @Transactional
    public WorkflowTemplate createTemplate(WorkflowTemplate template) {
        if (template.getTotalLevels() == null || template.getTotalLevels() <= 0) {
            throw new IllegalArgumentException("Total levels must be positive");
        }
        return templateRepository.save(template);
    }

    @Override
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

    @Override
    public WorkflowTemplate getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + id));
    }

    @Override
    public List<WorkflowTemplate> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override
    @Transactional
    public WorkflowTemplate activateTemplate(Long id) {
        WorkflowTemplate template = getTemplateById(id);
        template.setActive(true);
        return templateRepository.save(template);
    }

    @Override
    @Transactional
    public WorkflowTemplate deactivateTemplate(Long id) {
        WorkflowTemplate template = getTemplateById(id);
        template.setActive(false);
        return templateRepository.save(template);
    }
}