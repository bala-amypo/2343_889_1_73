package com.example.demo.controller;

import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.service.WorkflowStepConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
public class WorkflowStepConfigController {

    private final WorkflowStepConfigService service;

    public WorkflowStepConfigController(WorkflowStepConfigService service) {
        this.service = service;
    }

    @PostMapping
    public WorkflowStepConfig create(@RequestBody WorkflowStepConfig step) {
        return service.createStep(step);
    }

    @GetMapping("/template/{templateId}")
    public List<WorkflowStepConfig> getByTemplate(@PathVariable Long templateId) {
        return service.getStepsForTemplate(templateId);
    }
}
