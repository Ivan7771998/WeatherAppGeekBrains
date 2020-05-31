package com.example.weatherappgeekbrains.data;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

public class DataCitiesBuilder {
    private CityDao cityDao;

    public DataCitiesBuilder setResources(CityDao cityDao) {
        this.cityDao = cityDao;
        return this;
    }

    public IDataRecycler build(){
        DataCities dataCities = new DataCities(cityDao);
 //       dataCities.init();
        return dataCities;
    }
}
