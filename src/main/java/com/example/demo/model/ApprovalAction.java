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

    @Column(name = "level_number", nullable = false)
    private Integer levelNumber;

    @Column(name = "action", nullable = false)
    private String action;

    
    public ApprovalAction() {
    }

    public ApprovalAction(Long requestId, Integer levelNumber, String action) {
        this.requestId = requestId;
        this.levelNumber = levelNumber;
        this.action = action;
    }

   
    public Long getId() {
        return id;
    }

    
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

   
    public Integer getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
