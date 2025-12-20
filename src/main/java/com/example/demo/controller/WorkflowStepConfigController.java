package com.example.demo.controller;

import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.service.WorkflowStepConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
public class WorkflowStepConfigController {

    private final WorkflowStepConfigService stepConfigService;

    public WorkflowStepConfigController(WorkflowStepConfigService stepConfigService) {
        this.stepConfigService = stepConfigService;
    }

    @PostMapping
    public ResponseEntity<WorkflowStepConfig> createStep(@RequestBody WorkflowStepConfig stepConfig) {
        return ResponseEntity.ok(stepConfigService.createStep(stepConfig));
    }

    @GetMapping("/template/{templateId}")
    public ResponseEntity<List<WorkflowStepConfig>> getStepsForTemplate(@PathVariable Long templateId) {
        return ResponseEntity.ok(stepConfigService.getStepsForTemplate(templateId));
    }
}
