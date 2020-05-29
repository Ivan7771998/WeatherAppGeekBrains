package com.example.weatherappgeekbrains.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name_city"})})
public class EntityCity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name_city")
    public String nameCity;
}
