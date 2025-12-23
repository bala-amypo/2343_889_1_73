package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Role() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
