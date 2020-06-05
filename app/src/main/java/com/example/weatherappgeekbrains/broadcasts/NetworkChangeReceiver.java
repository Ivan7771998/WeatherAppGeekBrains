package com.example.weatherappgeekbrains.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.weatherappgeekbrains.tools.MySharedPref;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (intent.getExtras() != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                    Log.e("app", "Network " + ni.getTypeName() + " connected");
                    MySharedPref.setNetWorkConnect(true);
                } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                    Log.e("app", "There's no network connectivity");
                    MySharedPref.setNetWorkConnect(false);
                }
            }
        }
    }
}

