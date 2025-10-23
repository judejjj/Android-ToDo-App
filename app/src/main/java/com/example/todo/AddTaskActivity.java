package com.example.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    EditText titleEt, descEt;
    Spinner prioritySp, statusSp, categorySp;
    Button saveBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new DBHelper(this);

        titleEt = findViewById(R.id.titleEt);
        descEt = findViewById(R.id.descEt);
        prioritySp = findViewById(R.id.prioritySp);
        statusSp = findViewById(R.id.statusSp);
        categorySp = findViewById(R.id.categorySp);
        saveBtn = findViewById(R.id.saveBtn);

        // Set up spinners
        prioritySp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Low","Medium","High"}));
        statusSp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Pending","Completed"}));
        categorySp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Work","Personal","Other"}));

        saveBtn.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = titleEt.getText().toString().trim();
        String desc = descEt.getText().toString().trim();
        String priority = prioritySp.getSelectedItem().toString();
        String status = statusSp.getSelectedItem().toString();
        String category = categorySp.getSelectedItem().toString();

        if(title.isEmpty() || desc.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Task task = new Task(0, title, desc, priority, status, category, "", "", "", "Task");
            if(dbHelper.addTask(task)){
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
