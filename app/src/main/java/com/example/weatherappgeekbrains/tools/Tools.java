package com.example.weatherappgeekbrains.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.weatherappgeekbrains.R;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Tools {

    public static int[] getImageArray(Resources resources, int idArray) {
        @SuppressLint("Recycle")
        TypedArray pictures = resources.obtainTypedArray(idArray);
        int length = pictures.length();
        int[] answer = new int[length];
        for (int i = 0; i < length; i++) {
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    public static SharedPreferences newInstance(Context context) {
        return context.getSharedPreferences(Constants.NAME_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static String getDayWeek(Resources resources) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                return resources.getString(R.string.monday);
            case Calendar.TUESDAY:
                return resources.getString(R.string.tuesday);
            case Calendar.WEDNESDAY:
                return resources.getString(R.string.wednesday);
            case Calendar.THURSDAY:
                return resources.getString(R.string.thursday);
            case Calendar.FRIDAY:
                return resources.getString(R.string.friday);
            case Calendar.SATURDAY:
                return resources.getString(R.string.saturday);
            case Calendar.SUNDAY:
                return resources.getString(R.string.sunday);
        }
        return null;
    }
}
