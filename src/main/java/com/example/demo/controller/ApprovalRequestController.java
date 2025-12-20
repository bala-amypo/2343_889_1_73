package com.example.demo.controller;

import com.example.demo.model.ApprovalAction;
import com.example.demo.model.ApprovalRequest;
import com.example.demo.model.AuditLogRecord;
import com.example.demo.service.ApprovalActionService;
import com.example.demo.service.ApprovalRequestService;
import com.example.demo.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ApprovalRequestController {

    private final ApprovalRequestService requestService;
    private final ApprovalActionService actionService;
    private final AuditLogService auditLogService;

    public ApprovalRequestController(ApprovalRequestService requestService,
                                    ApprovalActionService actionService,
                                    AuditLogService auditLogService) {
        this.requestService = requestService;
        this.actionService = actionService;
        this.auditLogService = auditLogService;
    }

    @PostMapping
    public ResponseEntity<ApprovalRequest> createRequest(@RequestBody ApprovalRequest request) {
        return ResponseEntity.ok(requestService.createRequest(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprovalRequest> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @GetMapping("/requester/{requesterId}")
    public ResponseEntity<List<ApprovalRequest>> getRequestsByRequester(@PathVariable Long requesterId) {
        return ResponseEntity.ok(requestService.getRequestsByRequester(requesterId));
    }

    @GetMapping
    public ResponseEntity<List<ApprovalRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @PostMapping("/{id}/actions")
    public ResponseEntity<ApprovalAction> recordAction(@PathVariable Long id, 
                                                       @RequestBody ApprovalAction action) {
        action.setRequestId(id);
        return ResponseEntity.ok(actionService.recordAction(action));
    }

    @GetMapping("/{id}/audit")
    public ResponseEntity<List<AuditLogRecord>> getAuditLogs(@PathVariable Long id) {
        return ResponseEntity.ok(auditLogService.getLogsByRequestId(id));
    }
}