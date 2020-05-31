package com.example.weatherappgeekbrains.data;

import android.content.res.Resources;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.database.entities.EntityWeatherDesc;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

import java.util.ArrayList;
import java.util.List;

public class DataHistoryWeather implements IDataRecycler {
    private List<EntityWeatherDesc> entityWeatherDescList;
    private CityDao cityDao;

    DataHistoryWeather(CityDao cityDao) {
        this.entityWeatherDescList = new ArrayList<>();
        this.cityDao = cityDao;
        entityWeatherDescList = getAllHistoryWeather();
    }

    @Override
    public <T> T getData(int position) {
        return (T) entityWeatherDescList.get(position);
    }

    @Override
    public int size() {
        return entityWeatherDescList.size();
    }

    @Override
    public void addItem(String name) {
    }

    @Override
    public void removeCity(int position) {
        cityDao.deteleWeatherDescById(entityWeatherDescList.get(position).id);
        entityWeatherDescList = getAllHistoryWeather();
    }

    @Override
    public Resources getResources() {
        return null;
    }

    private List<EntityWeatherDesc> getAllHistoryWeather() {
        return cityDao.getAllHistoryWeather();
    }
}
