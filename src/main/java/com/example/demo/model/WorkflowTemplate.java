package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "workflow_templates")
public class WorkflowTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String templateName;

    private String description;
    private Integer totalLevels;
    private Boolean active;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkflowTemplate that = (WorkflowTemplate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
