package com.example.demo.controller;

import com.example.demo.model.WorkflowTemplate;
import com.example.demo.service.WorkflowTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class WorkflowTemplateController {

    private final WorkflowTemplateService workflowTemplateService;

    public WorkflowTemplateController(WorkflowTemplateService workflowTemplateService) {
        this.workflowTemplateService = workflowTemplateService;
    }

    @PostMapping
    public WorkflowTemplate createTemplate(@RequestBody WorkflowTemplate template) {
        return workflowTemplateService.createTemplate(template);
    }

    @GetMapping("/{id}")
    public WorkflowTemplate getTemplate(@PathVariable Long id) {
        return workflowTemplateService.getTemplateById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }

    @GetMapping
    public List<WorkflowTemplate> getAllTemplates() {
        return workflowTemplateService.getAllTemplates();
    }

    @PutMapping("/{id}")
    public WorkflowTemplate updateTemplate(
            @PathVariable Long id,
            @RequestBody WorkflowTemplate template
    ) {
        return workflowTemplateService.updateTemplate(id, template);
    }

    @PutMapping("/{id}/activate")
    public WorkflowTemplate activateTemplate(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        return workflowTemplateService.activateTemplate(id, active);
    }
}
