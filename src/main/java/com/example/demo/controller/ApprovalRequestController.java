package com.example.demo.controller;

import com.example.demo.model.ApprovalRequest;
import com.example.demo.service.ApprovalRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ApprovalRequestController {

    private final ApprovalRequestService service;

    public ApprovalRequestController(ApprovalRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ApprovalRequest create(@RequestBody ApprovalRequest request) {
        return service.createRequest(request);
    }

    @GetMapping
    public List<ApprovalRequest> getAll(
            @RequestParam(required = false) Long requesterId) {

        if (requesterId != null) {
            return service.getRequestsByRequester(requesterId);
        }
        return service.getAllRequests();
    }
}
