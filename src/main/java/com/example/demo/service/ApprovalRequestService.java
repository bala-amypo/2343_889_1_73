package com.example.demo.service;

import com.example.demo.model.ApprovalRequest;

import java.util.List;

public interface ApprovalRequestService {

    ApprovalRequest save(ApprovalRequest request);

    ApprovalRequest updateRequest(ApprovalRequest request);

    ApprovalRequest findById(Long id);

    List<ApprovalRequest> findAll();
}
