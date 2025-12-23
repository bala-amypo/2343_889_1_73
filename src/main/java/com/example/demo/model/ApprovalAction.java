package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "approval_actions")
public class ApprovalAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "request_id", nullable = false)
    private Long requestId;

    
    @Column(name = "level", nullable = false)
    private Integer level;

    
    @Column(name = "approver_id", nullable = false)
    private Long approverId;

    @Column(name = "action", nullable = false)
    private String action;

   

    public ApprovalAction() {
    }

    public ApprovalAction(Long requestId, Integer level, Long approverId, String action) {
        this.requestId = requestId;
        this.level = level;
        this.approverId = approverId;
        this.action = action;
    }

    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

   
    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
