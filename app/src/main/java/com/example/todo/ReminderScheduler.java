package com.example.todo;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class ReminderScheduler {

    private static final String CHANNEL_ID = "task_reminder_channel";

    public static void scheduleReminder(Context context, Calendar dateTime, int minutesBefore) {
        // create notification channel
        createNotificationChannel(context);

        Calendar alarmTime = (Calendar) dateTime.clone();
        alarmTime.add(Calendar.MINUTE, -minutesBefore);

        if(alarmTime.before(Calendar.getInstance())) return; // skip past alarms

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("title", "Task Reminder");
        intent.putExtra("message", "Your task is due in " + minutesBefore + " minutes!");

        int requestCode = (int) System.currentTimeMillis(); // unique request code
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager != null){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
        }
    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminders";
            String description = "Task reminder notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static String getChannelId() {
        return CHANNEL_ID;
    }
}
