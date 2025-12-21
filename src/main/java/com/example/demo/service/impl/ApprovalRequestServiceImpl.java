package com.example.demo.service.impl;

import com.example.demo.model.ApprovalRequest;
import com.example.demo.repository.ApprovalRequestRepository;
import com.example.demo.service.ApprovalRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalRequestServiceImpl implements ApprovalRequestService {

    private final ApprovalRequestRepository repository;

    public ApprovalRequestServiceImpl(ApprovalRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApprovalRequest createRequest(ApprovalRequest request) {
        request.setStatus("PENDING");
        request.setCurrentLevel(1);
        return repository.save(request);
    }

    @Override
    public List<ApprovalRequest> getRequestsByRequester(Long requesterId) {
        return repository.findByRequesterId(requesterId);
    }

    @Override
    public List<ApprovalRequest> getAllRequests() {
        return repository.findAll();
    }
}
