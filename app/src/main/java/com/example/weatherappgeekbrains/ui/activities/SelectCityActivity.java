package com.example.weatherappgeekbrains.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment;

public class SelectCityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
        if (savedInstanceState == null) {
            CoatOfArmsFragment details = new CoatOfArmsFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, details).commit();
        }

    }
}
