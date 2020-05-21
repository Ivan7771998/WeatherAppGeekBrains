package com.example.weatherappgeekbrains.ui.activities;

<<<<<<< Updated upstream
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
=======
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
>>>>>>> Stashed changes

import android.content.res.Configuration;
import android.os.Bundle;
<<<<<<< Updated upstream
import android.view.View;
import android.widget.FrameLayout;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.ui.fragments.AboutMeFragment;
import com.example.weatherappgeekbrains.ui.fragments.CitiesFragment;
import com.example.weatherappgeekbrains.ui.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
=======
import android.view.Menu;
import android.view.MenuItem;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.interfaces.IFragmentDialog;
import com.example.weatherappgeekbrains.ui.dialogs.DialogAboutApp;
import com.google.android.material.navigation.NavigationView;
>>>>>>> Stashed changes

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IFragmentDialog {

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
<<<<<<< Updated upstream
=======

    @Override
    public void callBackDialog() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.popBackStack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_about_app) {
            DialogAboutApp dialogAboutApp = DialogAboutApp.newInstance();
            dialogAboutApp.show(getSupportFragmentManager(), "DialogAboutApp");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
>>>>>>> Stashed changes
}