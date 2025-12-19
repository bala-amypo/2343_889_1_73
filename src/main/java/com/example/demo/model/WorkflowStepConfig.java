package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "workflow_step_configs")
public class WorkflowStepConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long templateId;

    @Column(nullable = false)
    private Integer levelNumber;

    @Column(nullable = false)
    private String approverRole;

    private Boolean isFinalStep = false;

    private String instructions;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }

    public Integer getLevelNumber() { return levelNumber; }
    public void setLevelNumber(Integer levelNumber) { this.levelNumber = levelNumber; }

    public String getApproverRole() { return approverRole; }
    public void setApproverRole(String approverRole) { this.approverRole = approverRole; }

    public Boolean getIsFinalStep() { return isFinalStep; }
    public void setIsFinalStep(Boolean isFinalStep) { this.isFinalStep = isFinalStep; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
