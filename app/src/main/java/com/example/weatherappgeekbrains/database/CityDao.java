package com.example.weatherappgeekbrains.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.database.entities.EntityCityAndWeatherDesc;
import com.example.weatherappgeekbrains.database.entities.EntityWeatherDesc;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherDesc(EntityWeatherDesc entityWeatherDesc);

    @Update
    void updateWeatherDesc(EntityWeatherDesc entityWeatherDesc);

    @Delete
    void deleteWeatherDesc(EntityWeatherDesc entityWeatherDesc);

    @Query("DELETE FROM entityWeatherDesc WHERE id = :id")
    void deteleWeatherDescById(long id);

    @Query("SELECT * FROM entityWeatherDesc")
    List<EntityWeatherDesc> getAllHistoryWeather();

    @Query("SELECT * FROM EntityWeatherDesc WHERE id = :id")
    EntityWeatherDesc getWeatherDescById(long id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(EntityCity city);

    @Update
    void updateCity(EntityCity city);

    @Delete
    void deleteCity(EntityCity city);

    @Query("DELETE FROM entityCity WHERE name_city = :city")
    void deteleCity(String city);

    @Query("SELECT * FROM EntityCity")
    List<EntityCity> getAllCities();

    @Query("SELECT * FROM EntityCity WHERE id = :id")
    EntityCity getCityById(long id);

    @Query("SELECT * FROM EntityCity WHERE  name_city = :city")
    EntityCity getCityByName(String city);

    @Query("SELECT * from EntityCity")
    @Transaction
    List<EntityCityAndWeatherDesc> loadEntityCityAndWeatherDesc();
}
