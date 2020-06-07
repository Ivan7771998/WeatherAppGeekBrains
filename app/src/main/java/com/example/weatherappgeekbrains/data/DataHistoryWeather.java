package com.example.weatherappgeekbrains.data;

import android.content.Context;
import android.content.res.Resources;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.database.entities.EntityCityAndWeatherDesc;
import com.example.weatherappgeekbrains.database.entities.EntityWeatherDesc;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

import java.util.ArrayList;
import java.util.List;

public class DataHistoryWeather implements IDataRecycler {
    private List<EntityCityAndWeatherDesc> entityWeatherDescList;
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
    public void addItem(String name, Context context) {
    }

    @Override
    public void removeCity(int position) {
//        cityDao.deteleWeatherDescById(entityWeatherDescList.get(position)
//                .entityWeatherDescList.get(entityWeatherDescList.size() - 1).id);
//        entityWeatherDescList = getAllHistoryWeather();
    }

    @Override
    public Resources getResources() {
        return null;
    }

    private List<EntityCityAndWeatherDesc> getAllHistoryWeather() {
        return cityDao.loadEntityCityAndWeatherDesc();
    }
}
