package com.example.weatherappgeekbrains.ui.activities;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCityActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        setTitleToolbar();
        initToolBar(toolbar);
        if (savedInstanceState == null) {
            CoatOfArmsFragment details = new CoatOfArmsFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, details).commit();
        }
    }

    private void setTitleToolbar() {
        toolbar.setTitle(getString(R.string.title_weather_in));
    }
}
