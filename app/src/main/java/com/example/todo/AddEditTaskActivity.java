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

import java.util.ArrayList;
import java.util.Calendar;

public class AddEditTaskActivity extends AppCompatActivity {

    EditText titleEt, descEt, deadlineEt, timeEt;
    Spinner prioritySp, statusSp, categorySp;
    CheckBox remind10, remind30, remind60;
    Button saveBtn;
    DBHelper dbHelper;
    Calendar selectedDateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

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

        prioritySp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Low","Medium","High"}));
        statusSp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Pending","Completed"}));
        categorySp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Work","Personal","Other"}));

        deadlineEt.setOnClickListener(v -> showDatePicker());
        timeEt.setOnClickListener(v -> showTimePicker());
        saveBtn.setOnClickListener(v -> saveTask());
    }

    private void showDatePicker(){
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    deadlineEt.setText(String.format("%04d-%02d-%02d", year, month+1, dayOfMonth));
                },
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

    private void showTimePicker(){
        Calendar c = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    timeEt.setText(String.format("%02d:%02d", hourOfDay, minute));
                },
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        tpd.show();
    }

    private void saveTask(){
        String title = titleEt.getText().toString().trim();
        String desc = descEt.getText().toString().trim();
        String priority = prioritySp.getSelectedItem().toString();
        String status = statusSp.getSelectedItem().toString();
        String category = categorySp.getSelectedItem().toString();
        String deadline = deadlineEt.getText().toString().trim() + " " + timeEt.getText().toString().trim();

        if(title.isEmpty() || desc.isEmpty() || deadlineEt.getText().toString().isEmpty() || timeEt.getText().toString().isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare reminders CSV
        ArrayList<String> reminderList = new ArrayList<>();
        if(remind10.isChecked()) reminderList.add("10");
        if(remind30.isChecked()) reminderList.add("30");
        if(remind60.isChecked()) reminderList.add("60");
        String remindersCsv = android.text.TextUtils.join(",", reminderList);

        // Corrected Task constructor
        Task task = new Task(0, title, desc, priority, status, category, deadline, remindersCsv);

        if(dbHelper.addTask(task)){
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            // schedule reminders
            for(String r : reminderList){
                ReminderScheduler.scheduleReminder(this, selectedDateTime, Integer.parseInt(r));
            }
            finish();
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_LONG).show();
        }
    }

}
