package com.example.weatherappgeekbrains.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherappgeekbrains.database.entities.EntityCity;

import java.util.List;

@Dao
public interface CityDao {

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
}
