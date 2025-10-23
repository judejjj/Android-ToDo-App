package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button tasksBtn, remindersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksBtn = findViewById(R.id.tasksBtn);
        remindersBtn = findViewById(R.id.remindersBtn);

        tasksBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TaskActivity.class)));

        remindersBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ReminderActivity.class)));
    }
}