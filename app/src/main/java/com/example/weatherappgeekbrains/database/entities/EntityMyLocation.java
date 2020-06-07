package com.example.weatherappgeekbrains.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EntityMyLocation {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "lat")
    public Double latitude;

    @ColumnInfo(name = "lon")
    public Double longitude;

    @ColumnInfo(name = "timestamp")
    public Long timestamp;
}
