package com.example.demo.controller;

import com.example.demo.model.ApprovalRequest;
import com.example.demo.service.ApprovalRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "Approval Requests", description = "Manage approval requests")
@SecurityRequirement(name = "Bearer Authentication")
public class ApprovalRequestController {
    private final ApprovalRequestService approvalRequestService;

    public ApprovalRequestController(ApprovalRequestService approvalRequestService) {
        this.approvalRequestService = approvalRequestService;
    }

    @PostMapping
    @Operation(summary = "Create a new approval request")
    public ResponseEntity<ApprovalRequest> createRequest(@RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(approvalRequestService.createRequest(request));
    }

    @GetMapping
    @Operation(summary = "Get all approval requests")
    public ResponseEntity<List<ApprovalRequest>> getAllRequests(
            @RequestParam(required = false) Long requesterId) {
        if (requesterId != null) {
            return ResponseEntity.ok(approvalRequestService.getRequestsByRequester(requesterId));
        }
        return ResponseEntity.ok(approvalRequestService.getAllRequests());
    }
}