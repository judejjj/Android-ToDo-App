package com.example.todo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        dbHelper = new DBHelper(this);

        int taskId = getIntent().getIntExtra("task_id", -1);

        TextView title = findViewById(R.id.detailTitle);
        TextView desc = findViewById(R.id.detailDesc);
        TextView type = findViewById(R.id.detailType);
        TextView dateTime = findViewById(R.id.detailDateTime);
        TextView reminders = findViewById(R.id.detailReminders);

        Task task = dbHelper.getTaskById(taskId);
        if (task != null) {
            title.setText(task.getTitle());
            desc.setText(task.getDescription());
            type.setText(task.getType());
            if ("Reminder".equals(task.getType())) {
                dateTime.setText(task.getDeadline() + " " + task.getStartTime());
                reminders.setText(task.getReminders());
            } else {
                dateTime.setText("");
                reminders.setText("");
            }
        }
    }
}





