package com.example.weatherappgeekbrains.tools;

import com.example.weatherappgeekbrains.App;

public class MySharedPref {
    public static boolean isDarkTheme() {
        return App.getInstance().getSharedPreferences().getBoolean(Constants.IS_DARK_THEME, false);
    }

    public static void setDarkTheme(boolean isDarkTheme) {
        App.getInstance().getSharedPreferences().edit().putBoolean(Constants.IS_DARK_THEME, isDarkTheme).apply();
    }

    public static void setCurrentCity(String nameCity) {
        App.getInstance().getSharedPreferences().edit().putString(Constants.CURRENT_CITY, nameCity).apply();
    }

    public static String getCurrentCity() {
        return App.getInstance().getSharedPreferences().getString(Constants.CURRENT_CITY, "");
    }
}
