package com.example.weatherappgeekbrains.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.example.weatherappgeekbrains.App;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.broadcasts.BatteryIndicatorBroadcast;
import com.example.weatherappgeekbrains.broadcasts.BroadcastWiFi;
import com.example.weatherappgeekbrains.broadcasts.NetworkChangeReceiver;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.MySharedPref;
import com.example.weatherappgeekbrains.tools.Tools;

public class BaseActivity extends AppCompatActivity {

    private BroadcastWiFi broadcastWiFi;
    private NetworkChangeReceiver networkChangeReceiver;
    private BatteryIndicatorBroadcast batteryIndicatorBroadcast;

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

    @Override
    protected void onStart() {
        super.onStart();
        //registrationWiFiBroadcast();
        registrationNetworkChangeReceiver();
        registrationBatteryBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(broadcastWiFi);
        unregisterReceiver(networkChangeReceiver);
        unregisterReceiver(batteryIndicatorBroadcast);
    }

    private void registrationWiFiBroadcast(){
        broadcastWiFi = new BroadcastWiFi();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broadcastWiFi, intentFilter);
    }

    private void registrationNetworkChangeReceiver(){
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }

    private void registrationBatteryBroadcast(){
        batteryIndicatorBroadcast = new BatteryIndicatorBroadcast();
        registerReceiver(batteryIndicatorBroadcast, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
    }
}
