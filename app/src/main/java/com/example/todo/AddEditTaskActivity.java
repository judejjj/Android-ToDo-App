package com.example.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEditTaskActivity extends AppCompatActivity {

    EditText titleEt, descEt, deadlineEt;
    Spinner prioritySp, statusSp, categorySp;
    Button saveBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        dbHelper = new DBHelper(this);

        titleEt = findViewById(R.id.titleEt);
        descEt = findViewById(R.id.descEt);
        deadlineEt = findViewById(R.id.deadlineEt);
        prioritySp = findViewById(R.id.prioritySp);
        statusSp = findViewById(R.id.statusSp);
        categorySp = findViewById(R.id.categorySp);
        saveBtn = findViewById(R.id.saveBtn);

        // populate spinners
        String[] priorities = {"High", "Medium", "Low"};
        String[] statuses = {"Pending", "Completed"};
        String[] categories = {"Work", "Study", "Personal", "Other"};

        prioritySp.setAdapter(new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, priorities));
        statusSp.setAdapter(new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statuses));
        categorySp.setAdapter(new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories));

        saveBtn.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = titleEt.getText().toString().trim();
        String desc = descEt.getText().toString().trim();
        String priority = prioritySp.getSelectedItem().toString();
        String status = statusSp.getSelectedItem().toString();
        String category = categorySp.getSelectedItem().toString();
        String deadline = deadlineEt.getText().toString().trim();

        if(title.isEmpty() || desc.isEmpty() || deadline.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean inserted = dbHelper.addTask(title, desc, priority, status, category, deadline);
        if(inserted){
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            finish(); // go back to MainActivity
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_LONG).show();
        }
    }
}
