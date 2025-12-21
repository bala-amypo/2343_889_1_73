package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approval_requests")
public class ApprovalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long templateId;
    private Long requesterId;
    private String requestTitle;

    @Lob
    private String requestPayloadJson;

    private String status = "PENDING";
    private Integer currentLevel = 1;
    private LocalDateTime createdAt = LocalDateTime.now();

    public ApprovalRequest() {}

    public Long getId() { return id; }

    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }

    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }

    public String getRequestTitle() { return requestTitle; }
    public void setRequestTitle(String requestTitle) { this.requestTitle = requestTitle; }

    public String getRequestPayloadJson() { return requestPayloadJson; }
    public void setRequestPayloadJson(String requestPayloadJson) { this.requestPayloadJson = requestPayloadJson; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(Integer currentLevel) { this.currentLevel = currentLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
