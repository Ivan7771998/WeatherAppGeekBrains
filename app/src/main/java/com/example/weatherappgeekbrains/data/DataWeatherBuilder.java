package com.example.weatherappgeekbrains.data;

import android.content.res.Resources;

import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

public class DataWeatherBuilder {
    private Resources resources;

    public DataWeatherBuilder setResources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public IDataRecycler build(){
        DataWeather dataWeather = new DataWeather(resources);
        dataWeather.init();
        return dataWeather;
    }
}
