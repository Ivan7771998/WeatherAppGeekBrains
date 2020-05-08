package com.example.weatherappgeekbrains.ui.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.ui.fragments.AboutMeFragment;
import com.example.weatherappgeekbrains.ui.fragments.CitiesFragment;
import com.example.weatherappgeekbrains.ui.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String ITEM_MENU = "currentItem";

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    FrameLayout secondFragment;
    private boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ITEM_MENU)) {
                navView.setSelectedItemId(savedInstanceState.getInt(ITEM_MENU));
            }
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.container_fragment,
                    new CitiesFragment()).commit();
        }
        changeOrientation();
        navView.setOnNavigationItemSelectedListener(listener);
    }

    private void changeOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            secondFragment = findViewById(R.id.coat_of_arms);
            isLandscape = true;
            changeStateVisibleSecondFrame(navView.getSelectedItemId());
        } else {
            isLandscape = false;
        }
    }

    private void changeStateVisibleSecondFrame(int currentItem) {
        if (currentItem == R.id.navigation_weather) {
            secondFragment.setVisibility(View.VISIBLE);
        } else {
            secondFragment.setVisibility(View.GONE);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = item -> {
        Fragment selectedFragment;
        switch (item.getItemId()) {
            case R.id.navigation_about_dev:
                selectedFragment = new AboutMeFragment();
                break;
            case R.id.navigation_settings:
                selectedFragment = new SettingsFragment();
                break;
            default:
                selectedFragment = new CitiesFragment();
        }
        if (isLandscape)
            changeStateVisibleSecondFrame(item.getItemId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, selectedFragment).commit();
        return true;
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ITEM_MENU, navView.getSelectedItemId());
    }
}