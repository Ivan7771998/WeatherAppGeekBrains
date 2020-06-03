package com.example.weatherappgeekbrains;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.database.WeatherDatabase;
import com.example.weatherappgeekbrains.network.Repository;
import com.example.weatherappgeekbrains.tools.Tools;
import com.google.firebase.messaging.FirebaseMessaging;


public class App extends Application {
    private static App instance;

    private WeatherDatabase db;
    private Repository repository;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        repository = new Repository();
        db = Room.databaseBuilder(
                getApplicationContext(),
                WeatherDatabase.class,
                "weather_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public CityDao getCityDao() {
        return db.getCityDao();
    }

    public SharedPreferences getSharedPreferences() {
        return Tools.newInstance(getApplicationContext());
    }

    public Repository getRepository() {
        return repository;
    }

}
