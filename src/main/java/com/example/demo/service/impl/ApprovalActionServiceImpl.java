package com.example.demo.service.impl;

import com.example.demo.model.ApprovalAction;
import com.example.demo.model.ApprovalRequest;
import com.example.demo.model.WorkflowStepConfig;
import com.example.demo.repository.ApprovalActionRepository;
import com.example.demo.service.ApprovalActionService;
import com.example.demo.service.ApprovalRequestService;
import com.example.demo.service.AuditLogService;
import com.example.demo.service.WorkflowStepConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApprovalActionServiceImpl implements ApprovalActionService {

    private final ApprovalActionRepository actionRepository;
    private final ApprovalRequestService requestService;
    private final WorkflowStepConfigService stepConfigService;
    private final AuditLogService auditLogService;

    public ApprovalActionServiceImpl(ApprovalActionRepository actionRepository,
                                    ApprovalRequestService requestService,
                                    WorkflowStepConfigService stepConfigService,
                                    AuditLogService auditLogService) {
        this.actionRepository = actionRepository;
        this.requestService = requestService;
        this.stepConfigService = stepConfigService;
        this.auditLogService = auditLogService;
    }

    @Override
    @Transactional
    public ApprovalAction recordAction(ApprovalAction action) {
        ApprovalAction savedAction = actionRepository.save(action);
        
        ApprovalRequest request = requestService.getRequestById(action.getRequestId());
        
        if ("APPROVED".equals(action.getAction())) {
            List<WorkflowStepConfig> steps = stepConfigService.getStepsForTemplate(request.getTemplateId());
            WorkflowStepConfig currentStep = steps.stream()
                    .filter(s -> s.getLevelNumber().equals(request.getCurrentLevel()))
                    .findFirst()
                    .orElse(null);
            
            if (currentStep != null && currentStep.getIsFinalStep()) {
                request.setStatus("APPROVED");
                auditLogService.logEvent(request.getId(), "REQUEST_APPROVED", "Request fully approved");
            } else {
                request.setCurrentLevel(request.getCurrentLevel() + 1);
                request.setStatus("PENDING");
                auditLogService.logEvent(request.getId(), "LEVEL_APPROVED", 
                        "Level " + action.getLevelNumber() + " approved, moved to level " + request.getCurrentLevel());
            }
        } else if ("REJECTED".equals(action.getAction())) {
            request.setStatus("REJECTED");
            auditLogService.logEvent(request.getId(), "REQUEST_REJECTED", 
                    "Request rejected at level " + action.getLevelNumber());
        }
        
        requestService.updateRequest(request);
        
        return savedAction;
    }

    @Override
    public List<ApprovalAction> findByLevelAndAction(Integer levelNumber, String action) {
        return actionRepository.findByLevelAndAction(levelNumber, action);
    }
}
