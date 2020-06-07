package com.example.weatherappgeekbrains.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.database.entities.EntityMyLocation;
import com.example.weatherappgeekbrains.database.entities.EntityWeatherDesc;

@Database(entities = {EntityCity.class, EntityWeatherDesc.class, EntityMyLocation.class}, version = 4, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}
