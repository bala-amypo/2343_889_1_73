package com.example.demo.controller;

import com.example.demo.model.WorkflowTemplate;
import com.example.demo.service.WorkflowTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class WorkflowTemplateController {

    private final WorkflowTemplateService service;

    public WorkflowTemplateController(WorkflowTemplateService service) {
        this.service = service;
    }

    @PostMapping
    public WorkflowTemplate create(@RequestBody WorkflowTemplate template) {
        return service.createTemplate(template);
    }

    @GetMapping("/{id}")
    public WorkflowTemplate get(@PathVariable Long id) {
        return service.getTemplateById(id).orElseThrow();
    }

    @GetMapping
    public List<WorkflowTemplate> getAll() {
        return service.getAllTemplates();
    }

    @PutMapping("/{id}")
    public WorkflowTemplate update(
            @PathVariable Long id,
            @RequestBody WorkflowTemplate template) {
        return service.updateTemplate(id, template);
    }
}

