package com.example.demo.service.impl;

import com.example.demo.model.WorkflowTemplate;
import com.example.demo.repository.WorkflowTemplateRepository;
import com.example.demo.service.WorkflowTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowTemplateServiceImpl implements WorkflowTemplateService {

    private final WorkflowTemplateRepository repository;

    public WorkflowTemplateServiceImpl(WorkflowTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public WorkflowTemplate save(WorkflowTemplate template) {
        return repository.save(template);
    }

    @Override
    public WorkflowTemplate findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkflowTemplate not found"));
    }

    @Override
    public List<WorkflowTemplate> findAll() {
        return repository.findAll();
    }
}
