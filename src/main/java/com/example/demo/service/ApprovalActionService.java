package com.example.demo.service;

import com.example.demo.model.ApprovalAction;
import com.example.demo.repository.ApprovalActionRepository;
import com.example.demo.repository.ApprovalRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class ApprovalActionService {
    private final ApprovalActionRepository approvalActionRepository;
    private final ApprovalRequestRepository approvalRequestRepository;

    public ApprovalActionService(
            ApprovalActionRepository approvalActionRepository,
            ApprovalRequestRepository approvalRequestRepository) {
        this.approvalActionRepository = approvalActionRepository;
        this.approvalRequestRepository = approvalRequestRepository;
    }

    public ApprovalAction recordAction(ApprovalAction action) {
        if (action.getActionDate() == null) {
            action.setActionDate(LocalDateTime.now());
        }
        return approvalActionRepository.save(action);
    }
}