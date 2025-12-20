package com.example.demo.controller;

import com.example.demo.model.WorkflowTemplate;
import com.example.demo.service.WorkflowTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class WorkflowTemplateController {

    private final WorkflowTemplateService templateService;

    public WorkflowTemplateController(WorkflowTemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    public ResponseEntity<WorkflowTemplate> createTemplate(@RequestBody WorkflowTemplate template) {
        return ResponseEntity.ok(templateService.createTemplate(template));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowTemplate> updateTemplate(@PathVariable Long id, 
                                                           @RequestBody WorkflowTemplate template) {
        return ResponseEntity.ok(templateService.updateTemplate(id, template));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowTemplate> getTemplate(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getTemplateById(id));
    }

    @GetMapping
    public ResponseEntity<List<WorkflowTemplate>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<WorkflowTemplate> activateTemplate(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.activateTemplate(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<WorkflowTemplate> deactivateTemplate(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.deactivateTemplate(id));
    }
}
