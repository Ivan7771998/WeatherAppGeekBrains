package com.example.weatherappgeekbrains.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class Tools {

    public static final String IS_DARK_THEME = "is_dark_theme";
    private static final String NAME_PREFERENCE = "my_shared_preference";

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
        return context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE);
    }
}
