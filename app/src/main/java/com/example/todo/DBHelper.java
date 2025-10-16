package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "todo.db";
    public static final int DB_VERSION = 3; // incremented for type column

    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";

    public static final String TABLE_TASKS = "tasks";
    public static final String TASK_ID = "id";
    public static final String TASK_TITLE = "title";
    public static final String TASK_DESC = "description";
    public static final String TASK_PRIORITY = "priority";
    public static final String TASK_STATUS = "status";
    public static final String TASK_CATEGORY = "category";
    public static final String TASK_TYPE = "type"; // new column: Task or Reminder
    public static final String TASK_DEADLINE = "deadline";
    public static final String TASK_START_TIME = "start_time";
    public static final String TASK_REMINDERS = "reminders";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_EMAIL + " TEXT UNIQUE," +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createUsers);

        String createTasks = "CREATE TABLE " + TABLE_TASKS + " (" +
                TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TASK_TITLE + " TEXT," +
                TASK_DESC + " TEXT," +
                TASK_PRIORITY + " TEXT," +
                TASK_STATUS + " TEXT," +
                TASK_CATEGORY + " TEXT," +
                TASK_TYPE + " TEXT," +       // new column
                TASK_DEADLINE + " TEXT," +
                TASK_START_TIME + " TEXT," +
                TASK_REMINDERS + " TEXT)";
        db.execSQL(createTasks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table and recreate with new column
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // User methods
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_EMAIL, email);
        cv.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, cv);
        return result != -1;
    }

    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_ID},
                COL_EMAIL + "=? AND " + COL_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Task methods
    public boolean addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_TITLE, task.getTitle());
        cv.put(TASK_DESC, task.getDescription());
        cv.put(TASK_PRIORITY, task.getPriority());
        cv.put(TASK_STATUS, task.getStatus());
        cv.put(TASK_CATEGORY, task.getCategory());
        cv.put(TASK_TYPE, task.getType());  // save type
        cv.put(TASK_DEADLINE, task.getDeadline());
        cv.put(TASK_START_TIME, task.getStartTime());
        cv.put(TASK_REMINDERS, task.getReminders());
        long result = db.insert(TABLE_TASKS, null, cv);
        return result != -1;
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASKS + " ORDER BY " + TASK_ID + " DESC", null);
    }

    public Task getTaskById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, null, TASK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            Task task = new Task(
                    cursor.getInt(cursor.getColumnIndexOrThrow(TASK_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_DESC)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_PRIORITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_STATUS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_CATEGORY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_DEADLINE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_START_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_REMINDERS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TASK_TYPE)) // new field
            );
            cursor.close();
            return task;
        }
        return null;
    }

    public boolean updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_TITLE, task.getTitle());
        cv.put(TASK_DESC, task.getDescription());
        cv.put(TASK_PRIORITY, task.getPriority());
        cv.put(TASK_STATUS, task.getStatus());
        cv.put(TASK_CATEGORY, task.getCategory());
        cv.put(TASK_TYPE, task.getType());
        cv.put(TASK_DEADLINE, task.getDeadline());
        cv.put(TASK_START_TIME, task.getStartTime());
        cv.put(TASK_REMINDERS, task.getReminders());
        int result = db.update(TABLE_TASKS, cv, TASK_ID + "=?", new String[]{String.valueOf(task.getId())});
        return result > 0;
    }

    public boolean deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TASKS, TASK_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
