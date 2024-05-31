package com.gpronoitis.todomanagment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;

    private boolean is_completed;

    public Todo(){}

    public Todo(Long id, String title, String description, boolean is_completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.is_completed = is_completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return is_completed;
    }

    public void setCompleted(boolean is_completed) {
        is_completed = is_completed;
    }
}
