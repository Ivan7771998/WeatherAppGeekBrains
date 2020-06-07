package com.example.weatherappgeekbrains.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.weatherappgeekbrains.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public static LatLng getCoordinateCity(String nameCity, Context context) {
        LatLng ll = null;
        if (Geocoder.isPresent()) {
            try {
                Geocoder gc = new Geocoder(context);
                List<Address> addresses = gc.getFromLocationName(nameCity, 1);
                for (Address a : addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll = new LatLng(a.getLatitude(), a.getLongitude());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ll;
    }

    public static void setAddress(final LatLng location, Context context, TextView nameCity) {
        final Geocoder geocoder = new Geocoder(context);
        // Поскольку Geocoder работает по интернету, создаём отдельный поток
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                    nameCity.post(new Runnable() {
                        @Override
                        public void run() {
                            nameCity.setText(addresses.get(0).getAddressLine(0));
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
