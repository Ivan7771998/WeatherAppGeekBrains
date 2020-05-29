package com.example.weatherappgeekbrains;

import android.app.Application;

import androidx.room.Room;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.database.WeatherDatabase;

public class App extends Application {
    private static App instance;

    private WeatherDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


        db = Room.databaseBuilder(
                getApplicationContext(),
                WeatherDatabase.class,
                "weather_database")
                .allowMainThreadQueries()
                .build();
    }

    public CityDao getCityDao() {
        return db.getCityDao();
    }

}
