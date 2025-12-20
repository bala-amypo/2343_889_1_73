package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ApprovalRequest;
import com.example.demo.repository.ApprovalRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApprovalRequestService {

    private final ApprovalRequestRepository requestRepository;

    public ApprovalRequestService(ApprovalRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional
    public ApprovalRequest createRequest(ApprovalRequest request) {
        if (request.getStatus() == null || request.getStatus().isEmpty()) {
            request.setStatus("PENDING");
        }
        if (request.getCurrentLevel() == null) {
            request.setCurrentLevel(1);
        }
        return requestRepository.save(request);
    }

    public ApprovalRequest getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + id));
    }

    public List<ApprovalRequest> getRequestsByRequester(Long requesterId) {
        return requestRepository.findByRequesterId(requesterId);
    }

    public List<ApprovalRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @Transactional
    public ApprovalRequest updateRequest(ApprovalRequest request) {
        return requestRepository.save(request);
    }
}