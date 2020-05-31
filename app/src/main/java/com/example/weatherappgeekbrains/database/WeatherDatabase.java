package com.example.weatherappgeekbrains.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weatherappgeekbrains.database.entities.EntityCity;

@Database(entities = {EntityCity.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}
