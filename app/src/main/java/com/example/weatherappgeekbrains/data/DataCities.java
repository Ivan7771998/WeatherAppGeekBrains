package com.example.weatherappgeekbrains.data;

import android.content.res.Resources;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

import java.util.ArrayList;
import java.util.List;

public class DataCities implements IDataRecycler {

    private List<EntityCity> cityModelList;
    private CityDao cityDao;


    DataCities(CityDao cityDao) {
        this.cityModelList = new ArrayList<>();
        this.cityDao = cityDao;
        cityModelList = getAllCities();
    }

    @Override
    public <T> T getData(int position) {
        return (T) cityModelList.get(position);
    }

    @Override
    public int size() {
        return cityModelList.size();
    }

    @Override
    public void addItem(String name) {
        EntityCity entityCity = new EntityCity();
        entityCity.nameCity = name;
        cityDao.insertCity(entityCity);
        cityModelList = getAllCities();
    }

    @Override
    public void removeCity(int position) {
        cityDao.deteleCity(cityModelList.get(position).nameCity);
        cityModelList = getAllCities();
    }

    @Override
    public Resources getResources() {
        return null;
    }

    private List<EntityCity> getAllCities() {
        return cityDao.getAllCities();
    }
}
