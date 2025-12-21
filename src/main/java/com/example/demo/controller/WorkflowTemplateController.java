package com.example.demo.controller;

import com.example.demo.model.WorkflowTemplate;
import com.example.demo.service.WorkflowTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflow-templates")
public class WorkflowTemplateController {

    private final WorkflowTemplateService workflowTemplateService;

    public WorkflowTemplateController(WorkflowTemplateService workflowTemplateService) {
        this.workflowTemplateService = workflowTemplateService;
    }

    @PostMapping
    public WorkflowTemplate create(@RequestBody WorkflowTemplate template) {
        return workflowTemplateService.save(template);
    }

    @GetMapping("/{id}")
    public WorkflowTemplate getById(@PathVariable Long id) {
        
        return workflowTemplateService.findById(id);
    }

    @GetMapping
    public List<WorkflowTemplate> getAll() {
        return workflowTemplateService.findAll();
    }
}
