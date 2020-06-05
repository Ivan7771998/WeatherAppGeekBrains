package com.example.weatherappgeekbrains.broadcasts;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.MySharedPref;

import static android.content.Context.NOTIFICATION_SERVICE;

public class BatteryIndicatorBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra("level", 0);
        if (level < 20 && !MySharedPref.getNotifyBattery()) {
            Toast.makeText(context, "Низкий уровень аккумулятора!", Toast.LENGTH_SHORT).show();
            sendNotificationBattery(context);
            MySharedPref.setNotifyBattery(true);
        } else {
            MySharedPref.setNotifyBattery(false);
        }
    }

    private void sendNotificationBattery(Context context) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_lock_idle_low_battery)
                        .setContentTitle("Низкий уровень аккумулятора!")
                        .setContentText("Пожалуйста, поставьте на зарядку устройство!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null)
            notificationManager.notify(1, notification);
    }
}
