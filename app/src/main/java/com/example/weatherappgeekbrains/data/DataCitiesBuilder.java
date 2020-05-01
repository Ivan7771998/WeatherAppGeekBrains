package com.example.weatherappgeekbrains.data;

import android.content.res.Resources;

import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

public class DataCitiesBuilder {
    private Resources resources;

    public DataCitiesBuilder setResources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public IDataRecycler build(){
        DataCities dataCities = new DataCities(resources);
        dataCities.init();
        return dataCities;
    }
}
