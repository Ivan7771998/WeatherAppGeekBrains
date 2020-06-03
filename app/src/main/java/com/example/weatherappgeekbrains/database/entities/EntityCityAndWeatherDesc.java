package com.example.weatherappgeekbrains.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class EntityCityAndWeatherDesc {
    @Embedded
    public EntityCity entityCity;
    @Relation(parentColumn = "id", entityColumn = "entityCityId", entity = EntityWeatherDesc.class)
    public List<EntityWeatherDesc> entityWeatherDescList;
}
