package com.example.demo.service;

import com.example.demo.model.ApprovalRequest;
import com.example.demo.repository.ApprovalRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApprovalRequestServiceTest {

    @Autowired
    private ApprovalRequestService approvalRequestService;

    @Autowired
    private ApprovalRequestRepository repository;

    @Test
    void testCreateApprovalRequest() {
        ApprovalRequest request = new ApprovalRequest();
        request.setRequesterId(1L);
        request.setTemplateId(1L);
        request.setRequestTitle("Test Request");

        ApprovalRequest saved = approvalRequestService.createRequest(request);

        assertNotNull(saved.getId());
        assertEquals("PENDING", saved.getStatus());
        assertEquals(1, saved.getCurrentLevel());
    }
}
