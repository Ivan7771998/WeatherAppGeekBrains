package com.example.weatherappgeekbrains.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class EntityWeatherDesc {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "entityCityId")
    public long entityCityId;

    @ColumnInfo(name = "temperature")
    public String txtTemperature;

    @ColumnInfo(name = "description")
    public String txtDescription;

    @ColumnInfo(name = "date")
    public String txtDate;

    @ColumnInfo(name = "img")
    public String txtImg;
}
