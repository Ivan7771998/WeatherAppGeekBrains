package com.example.weatherappgeekbrains.tools;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
@SuppressLint("SimpleDateFormat")
public class UntilTimes {

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getAllDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeFromMil(Long longDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = Date.from(Instant.ofEpochSecond(longDate));
        return formatter.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeForDaily(Long longDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM");
        Date date = Date.from(Instant.ofEpochSecond(longDate));
        return formatter.format(date);
    }
}
