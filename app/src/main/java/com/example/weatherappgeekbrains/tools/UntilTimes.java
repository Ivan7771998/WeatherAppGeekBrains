package com.example.weatherappgeekbrains.tools;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class UntilTimes {
    @SuppressLint("SimpleDateFormat")
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
    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromMil(Long longDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = Date.from(Instant.ofEpochSecond(longDate));
        return formatter.format(date);
    }
}
