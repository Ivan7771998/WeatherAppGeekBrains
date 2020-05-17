package com.example.weatherappgeekbrains.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.Tools;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }
    }

    public boolean isDarkTheme() {
        SharedPreferences sharedPreferences = Tools.newInstance(getApplicationContext());
        return sharedPreferences.getBoolean(Constants.IS_DARK_THEME, false);
    }

    public void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPreferences = Tools.newInstance(getApplicationContext());
        sharedPreferences.edit().putBoolean(Constants.IS_DARK_THEME, isDarkTheme).apply();
    }
}
