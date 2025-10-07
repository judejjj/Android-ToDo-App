package com.example.todo;

public class Task {
    private int id;
    private String title, description, priority, status, category, deadline;

    public Task(int id, String title, String description, String priority, String status, String category, String deadline){
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.deadline = deadline;
    }

    // Getter and Setter
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getCategory() { return category; }
    public String getDeadline() { return deadline; }

    public void setStatus(String status){ this.status = status; }
}
