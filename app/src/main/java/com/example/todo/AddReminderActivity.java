package com.example.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {

    EditText titleEt, descEt, deadlineEt, timeEt;
    Spinner prioritySp, statusSp, categorySp;
    CheckBox remind10, remind30, remind60;
    Button saveBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        dbHelper = new DBHelper(this);

        titleEt = findViewById(R.id.titleEt);
        descEt = findViewById(R.id.descEt);
        deadlineEt = findViewById(R.id.deadlineEt);
        timeEt = findViewById(R.id.timeEt);
        prioritySp = findViewById(R.id.prioritySp);
        statusSp = findViewById(R.id.statusSp);
        categorySp = findViewById(R.id.categorySp);
        remind10 = findViewById(R.id.remind10);
        remind30 = findViewById(R.id.remind30);
        remind60 = findViewById(R.id.remind60);
        saveBtn = findViewById(R.id.saveBtn);

        // Set up spinners
        prioritySp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Low","Medium","High"}));
        statusSp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Pending","Completed"}));
        categorySp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Work","Personal","Other"}));

        // Set up date picker
        deadlineEt.setOnClickListener(v -> showDatePicker());
        deadlineEt.setFocusable(false);
        deadlineEt.setClickable(true);

        // Set up time picker
        timeEt.setOnClickListener(v -> showTimePicker());
        timeEt.setFocusable(false);
        timeEt.setClickable(true);

        saveBtn.setOnClickListener(v -> saveReminder());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    deadlineEt.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minute);
                    timeEt.setText(time);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    private void saveReminder() {
        String title = titleEt.getText().toString().trim();
        String desc = descEt.getText().toString().trim();
        String deadline = deadlineEt.getText().toString().trim();
        String time = timeEt.getText().toString().trim();
        String priority = prioritySp.getSelectedItem().toString();
        String status = statusSp.getSelectedItem().toString();
        String category = categorySp.getSelectedItem().toString();

        if(title.isEmpty() || desc.isEmpty() || deadline.isEmpty() || time.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Build reminder string
            StringBuilder reminders = new StringBuilder();
            if(remind10.isChecked()) reminders.append("10,");
            if(remind30.isChecked()) reminders.append("30,");
            if(remind60.isChecked()) reminders.append("60,");
            if(reminders.length() > 0) reminders.setLength(reminders.length() - 1); // Remove last comma

            Task reminder = new Task(0, title, desc, priority, status, category, deadline, time, reminders.toString(), "Reminder");
            if(dbHelper.addTask(reminder)){
                Toast.makeText(this, "Reminder added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add reminder", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
