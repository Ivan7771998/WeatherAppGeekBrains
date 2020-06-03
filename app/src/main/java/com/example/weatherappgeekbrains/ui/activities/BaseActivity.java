package com.example.weatherappgeekbrains.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.weatherappgeekbrains.App;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.MySharedPref;
import com.example.weatherappgeekbrains.tools.Tools;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MySharedPref.isDarkTheme()) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }
    }

    void initToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }
}
