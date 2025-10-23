package com.example.todo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    TaskAdapter adapter;
    ArrayList<Task> taskList;
    Button addBtn;
    TextView emptyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addBtn);
        emptyTv = findViewById(R.id.emptyTv);

        taskList = new ArrayList<>();

        adapter = new TaskAdapter(this, taskList, new TaskActionListener());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(v ->
                startActivity(new Intent(TaskActivity.this, AddTaskActivity.class)));

        loadTasks();
    }

    private void loadTasks() {
        taskList.clear();
        Cursor cursor = dbHelper.getAllTasks(); // Only get tasks, not reminders

        if (cursor != null && cursor.moveToFirst()) {
            emptyTv.setVisibility(TextView.GONE);
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.TASK_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_TITLE));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_DESC));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_PRIORITY));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_STATUS));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_CATEGORY));
                String deadline = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_DEADLINE));
                String reminders = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_REMINDERS));
                String startTime = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_START_TIME));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TASK_TYPE));

                // Only add tasks (not reminders)
                if ("Task".equals(type)) {
                    taskList.add(new Task(id, title, desc, priority, status, category, deadline, startTime, reminders, type));
                }
            } while (cursor.moveToNext());
        } else {
            emptyTv.setVisibility(TextView.VISIBLE);
        }

        if (cursor != null) cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    private class TaskActionListener implements TaskAdapter.OnTaskActionListener {
        @Override
        public void onDelete(Task task) {
            if (dbHelper.deleteTask(task.getId())) {
                Toast.makeText(TaskActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                loadTasks();
            }
        }

        @Override
        public void onMarkDone(Task task) {
            if (dbHelper.updateTask(task)) {
                Toast.makeText(TaskActivity.this,
                        task.getStatus().equals("Completed") ? "Marked done" : "Marked pending",
                        Toast.LENGTH_SHORT).show();
                loadTasks();
            }
        }
    }
}
