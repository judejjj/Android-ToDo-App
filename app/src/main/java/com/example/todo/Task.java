package com.example.todo;

public class Task {
    private int id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String category;
    private String deadline;
    private String startTime;
    private String reminders;

    public Task(int id, String title, String description, String priority, String status,
                String category, String deadline, String startTime, String reminders) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.deadline = deadline;
        this.startTime = startTime;
        this.reminders = reminders;
    }

    // Constructor without startTime (for backward compatibility)
    public Task(int id, String title, String description, String priority, String status,
                String category, String deadline, String reminders) {
        this(id, title, description, priority, status, category, deadline, "", reminders);
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getCategory() { return category; }
    public String getDeadline() { return deadline; }
    public String getStartTime() { return startTime; }
    public String getReminders() { return reminders; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setStatus(String status) { this.status = status; }
    public void setCategory(String category) { this.category = category; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setReminders(String reminders) { this.reminders = reminders; }
}
