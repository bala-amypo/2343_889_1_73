package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "workflow_templates")
public class WorkflowTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String templateName;

    private String description;

    private Integer totalLevels;

    private Boolean active;

    public WorkflowTemplate() {}

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTotalLevels() { return totalLevels; }
    public void setTotalLevels(Integer totalLevels) { this.totalLevels = totalLevels; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
