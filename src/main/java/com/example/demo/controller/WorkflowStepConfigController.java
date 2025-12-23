package com.example.demo.controller;

import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.service.WorkflowStepConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/steps")
@Tag(name = "Workflow Steps", description = "Manage workflow step configurations")
@SecurityRequirement(name = "Bearer Authentication")
public class WorkflowStepConfigController {
    private final WorkflowStepConfigService workflowStepConfigService;

    public WorkflowStepConfigController(WorkflowStepConfigService workflowStepConfigService) {
        this.workflowStepConfigService = workflowStepConfigService;
    }

    @PostMapping
    @Operation(summary = "Create a new workflow step")
    public ResponseEntity<WorkflowStepConfig> createStep(@RequestBody WorkflowStepConfig step) {
        return ResponseEntity.ok(workflowStepConfigService.createStep(step));
    }

    @GetMapping("/template/{templateId}")
    @Operation(summary = "Get all steps for a template")
    public ResponseEntity<List<WorkflowStepConfig>> getStepsForTemplate(@PathVariable Long templateId) {
        return ResponseEntity.ok(workflowStepConfigService.getStepsForTemplate(templateId));
    }
}