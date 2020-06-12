package com.example.weatherappgeekbrains.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.weatherappgeekbrains.App;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.database.entities.EntityMyLocation;
import com.example.weatherappgeekbrains.interfaces.IFragmentDialog;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.ui.dialogs.DialogAboutApp;
import com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment.CITY_DATA_LAT;
import static com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment.CITY_DATA_LNG;

public class MainActivity extends BaseActivity implements IFragmentDialog {

    //Notification
    private static int notifyID = 0;

    private AppBarConfiguration mAppBarConfiguration;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar(toolbar);
        initDrawer();
        createNotificationChannel();
        getCurrentToken();
    }

    private void initDrawer() {
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_weather,
                R.id.nav_about_dev,
                R.id.nav_settings,
                R.id.nav_send_push_notify,
                R.id.nav_find_out_the_weather)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_send_push_notify:
                    sendPushNotification();
                    break;
                case R.id.nav_find_out_the_weather:
                    try {
                        EntityMyLocation entityMyLocation = App.getInstance().getCityDao().getCurrentLocation();
                        LatLng coordinate = new LatLng(entityMyLocation.latitude, entityMyLocation.longitude);
                        showCoatOfArms(coordinate);
                    } catch (Exception e) {
                        Toast.makeText(this, getText(R.string.geolocation_not_found),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            NavigationUI.onNavDestinationSelected(item, navController);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void showCoatOfArms(LatLng coordinate) {
        Bundle args = new Bundle();
        args.putDouble(CITY_DATA_LAT, coordinate.latitude);
        args.putDouble(CITY_DATA_LNG, coordinate.longitude);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_weather_to_nav_selected_city_weather, args);
    }

    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

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

    private void sendPushNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle("Привет")
                        .setContentText("Это локальное пуш уведомление!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null)
            notificationManager.notify(notifyID++, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, getString(R.string.my_channel), importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }
    }

    private void getCurrentToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "getInstanceId failed", task.getException());
                        return;
                    }
                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    Log.d("TAG", token);
                });
    }
}