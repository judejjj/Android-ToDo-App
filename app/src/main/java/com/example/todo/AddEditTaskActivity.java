package com.example.todo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;

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

        // Adapters for spinners
        prioritySp.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Low", "Medium", "High"}));
        statusSp.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Pending", "Completed"}));
        categorySp.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Work", "Personal", "Other"}));

        deadlineEt.setOnClickListener(v -> showDatePicker());

        saveBtn.setOnClickListener(v -> saveTask());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) ->
                        deadlineEt.setText(String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis()); // block past dates
        dpd.show();
    }

    private void saveTask() {
        String title = titleEt.getText().toString().trim();
        String desc = descEt.getText().toString().trim();
        String priority = prioritySp.getSelectedItem().toString();
        String status = statusSp.getSelectedItem().toString();
        String category = categorySp.getSelectedItem().toString();
        String deadline = deadlineEt.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || deadline.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(0, title, desc, priority, status, category, deadline);
        if (dbHelper.addTask(task)) {
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            finish(); // go back to MainActivity
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_LONG).show();
        }
    }
}
