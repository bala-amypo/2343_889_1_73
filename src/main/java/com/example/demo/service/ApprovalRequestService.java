package com.example.demo.service;

import com.example.demo.model.ApprovalRequest;
import java.util.List;

public interface ApprovalRequestService {

    ApprovalRequest createRequest(ApprovalRequest request);

    ApprovalRequest updateRequest(ApprovalRequest request);

    ApprovalRequest findById(Long id);

    List<ApprovalRequest> getRequestsByRequester(Long requesterId);

    List<ApprovalRequest> getAllRequests();
}
