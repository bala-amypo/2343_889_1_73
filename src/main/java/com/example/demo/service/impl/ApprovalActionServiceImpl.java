package com.example.demo.service.impl;

import com.example.demo.model.ApprovalAction;
import com.example.demo.model.ApprovalRequest;
import com.example.demo.repository.ApprovalActionRepository;
import com.example.demo.repository.ApprovalRequestRepository;
import com.example.demo.service.ApprovalActionService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApprovalActionServiceImpl implements ApprovalActionService {

    private final ApprovalActionRepository approvalActionRepository;
    private final ApprovalRequestRepository approvalRequestRepository;

    public ApprovalActionServiceImpl(
            ApprovalActionRepository approvalActionRepository,
            ApprovalRequestRepository approvalRequestRepository
    ) {
        this.approvalActionRepository = approvalActionRepository;
        this.approvalRequestRepository = approvalRequestRepository;
    }

    @Override
    public ApprovalAction recordAction(ApprovalAction action) {
        ApprovalRequest request = approvalRequestRepository.findById(action.getRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("ApprovalRequest not found"));

        return approvalActionRepository.save(action);
    }
}
