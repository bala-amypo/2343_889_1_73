package com.example.demo.service;

import com.example.demo.model.ApprovalRequest;
import com.example.demo.repository.ApprovalRequestRepository;
import com.example.demo.repository.WorkflowStepConfigRepository;
import com.example.demo.repository.WorkflowTemplateRepository;
import com.example.demo.repository.ApprovalActionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ApprovalRequestService {
    private final ApprovalRequestRepository approvalRequestRepository;
    private final WorkflowStepConfigRepository workflowStepConfigRepository;
    private final WorkflowTemplateRepository workflowTemplateRepository;
    private final ApprovalActionRepository approvalActionRepository;

    public ApprovalRequestService(
            ApprovalRequestRepository approvalRequestRepository,
            WorkflowStepConfigRepository workflowStepConfigRepository,
            WorkflowTemplateRepository workflowTemplateRepository,
            ApprovalActionRepository approvalActionRepository) {
        this.approvalRequestRepository = approvalRequestRepository;
        this.workflowStepConfigRepository = workflowStepConfigRepository;
        this.workflowTemplateRepository = workflowTemplateRepository;
        this.approvalActionRepository = approvalActionRepository;
    }

    public ApprovalRequest createRequest(ApprovalRequest request) {
        if (request.getStatus() == null) {
            request.setStatus("PENDING");
        }
        if (request.getCurrentLevel() == null) {
            request.setCurrentLevel(1);
        }
        if (request.getCreatedAt() == null) {
            request.setCreatedAt(LocalDateTime.now());
        }
        return approvalRequestRepository.save(request);
    }

    public List<ApprovalRequest> getRequestsByRequester(Long userId) {
        return approvalRequestRepository.findByRequesterId(userId);
    }

    public List<ApprovalRequest> getAllRequests() {
        return approvalRequestRepository.findAll();
    }

    public ApprovalRequest updateRequestStatus(Long id, String status, Integer currentLevel) {
        ApprovalRequest request = approvalRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        if (currentLevel != null) {
            request.setCurrentLevel(currentLevel);
        }
        return approvalRequestRepository.save(request);
    }
}