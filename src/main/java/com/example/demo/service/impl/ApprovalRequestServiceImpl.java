package com.example.demo.service.impl;

import com.example.demo.model.ApprovalRequest;
import com.example.demo.repository.ApprovalActionRepository;
import com.example.demo.repository.ApprovalRequestRepository;
import com.example.demo.repository.WorkflowStepConfigRepository;
import com.example.demo.repository.WorkflowTemplateRepository;
import com.example.demo.service.ApprovalRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalRequestServiceImpl implements ApprovalRequestService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final WorkflowStepConfigRepository workflowStepConfigRepository;
    private final WorkflowTemplateRepository workflowTemplateRepository;
    private final ApprovalActionRepository approvalActionRepository;

    public ApprovalRequestServiceImpl(
            ApprovalRequestRepository approvalRequestRepository,
            WorkflowStepConfigRepository workflowStepConfigRepository,
            WorkflowTemplateRepository workflowTemplateRepository,
            ApprovalActionRepository approvalActionRepository
    ) {
        this.approvalRequestRepository = approvalRequestRepository;
        this.workflowStepConfigRepository = workflowStepConfigRepository;
        this.workflowTemplateRepository = workflowTemplateRepository;
        this.approvalActionRepository = approvalActionRepository;
    }

    @Override
    public ApprovalRequest createRequest(ApprovalRequest request) {
        request.setStatus("PENDING");   
        request.setCurrentLevel(1);
        return approvalRequestRepository.save(request);
    }

    @Override
    public List<ApprovalRequest> getRequestsByRequester(Long requesterId) {
        return approvalRequestRepository.findByRequesterId(requesterId);
    }

    @Override
    public List<ApprovalRequest> getAllRequests() {
        return approvalRequestRepository.findAll();
    }
}
