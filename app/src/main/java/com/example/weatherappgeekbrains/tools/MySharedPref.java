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

    public static void setNetWorkConnect(boolean netWorkConnect) {
        App.getInstance().getSharedPreferences().edit().putBoolean(Constants.NETWORK, netWorkConnect).apply();
    }

    public static boolean getNetWorkConnect() {
        return App.getInstance().getSharedPreferences().getBoolean(Constants.NETWORK, false);
    }

    public static void setNotifyBattery(boolean notify) {
        App.getInstance().getSharedPreferences().edit().putBoolean(Constants.NOTIFY_BATTERY, notify).apply();
    }

    public static boolean getNotifyBattery() {
        return App.getInstance().getSharedPreferences().getBoolean(Constants.NOTIFY_BATTERY, false);
    }
}
