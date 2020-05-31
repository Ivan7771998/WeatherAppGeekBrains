package com.example.weatherappgeekbrains.data;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

public class DataHistoryBuilder {
    private CityDao cityDao;

    public DataHistoryBuilder setResources(CityDao cityDao) {
        this.cityDao = cityDao;
        return this;
    }

    public IDataRecycler build() {
        return new DataHistoryWeather(cityDao);
    }
}
