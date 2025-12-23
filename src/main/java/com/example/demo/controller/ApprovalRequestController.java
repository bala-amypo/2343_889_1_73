package com.example.demo.controller;

import com.example.demo.model.ApprovalRequest;
import com.example.demo.service.ApprovalRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ApprovalRequestController {

    private final ApprovalRequestService approvalRequestService;

    public ApprovalRequestController(ApprovalRequestService approvalRequestService) {
        this.approvalRequestService = approvalRequestService;
    }

    @PostMapping
    public ApprovalRequest createRequest(@RequestBody ApprovalRequest request) {
        return approvalRequestService.createRequest(request);
    }

    @GetMapping
    public List<ApprovalRequest> getRequests(
            @RequestParam(required = false) Long requesterId
    ) {
        if (requesterId != null) {
            return approvalRequestService.getRequestsByRequester(requesterId);
        }
        return approvalRequestService.getAllRequests();
    }
}
