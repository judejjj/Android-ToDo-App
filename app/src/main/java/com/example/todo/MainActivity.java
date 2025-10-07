package com.example.todo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    TaskAdapter adapter;
    ArrayList<Task> taskList;
    FloatingActionButton addBtn;
    TextView emptyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addBtn);
        emptyTv = findViewById(R.id.emptyTv);

        taskList = new ArrayList<>();

        adapter = new TaskAdapter(this, taskList, new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onDelete(Task task) {
                dbHelper.deleteTask(task.getId());
                loadTasks();
                Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkDone(Task task) {
                task.setStatus("Completed");
                dbHelper.updateTask(task.getId(), task.getTitle(), task.getDescription(),
                        task.getPriority(), task.getStatus(), task.getCategory(), task.getDeadline());
                loadTasks();
                Toast.makeText(MainActivity.this, "Task marked done", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(v -> startActivity(new Intent(this, AddEditTaskActivity.class)));

        loadTasks();
    }

    private void loadTasks(){
        taskList.clear();
        Cursor cursor = dbHelper.getAllTasks();
        if(cursor != null && cursor.moveToFirst()){
            emptyTv.setVisibility(View.GONE);
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                String deadline = cursor.getString(cursor.getColumnIndexOrThrow("deadline"));

                taskList.add(new Task(id, title, desc, priority, status, category, deadline));
            } while(cursor.moveToNext());
        } else {
            emptyTv.setVisibility(View.VISIBLE);
        }

        if(cursor != null) cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
}
