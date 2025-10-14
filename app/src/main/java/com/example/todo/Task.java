package com.example.todo;

public class Task {
    private int id;
    private String title, description, priority, status, category, deadline;
    private String reminders; // e.g., "10,30,60"

    public Task(int id, String title, String description, String priority, String status, String category, String deadline, String reminders){
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.deadline = deadline;
        this.reminders = reminders;
    }

    // Existing getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getCategory() { return category; }
    public String getDeadline() { return deadline; }

    public void setStatus(String status){ this.status = status; }

    // New reminders getters/setters
    public String getReminders() { return reminders; }
    public void setReminders(String reminders) { this.reminders = reminders; }
}
