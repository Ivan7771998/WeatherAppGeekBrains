package com.example.weatherappgeekbrains.data;

import android.content.Context;
import android.content.res.Resources;

import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.tools.Tools;
import com.google.android.gms.maps.model.LatLng;

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
    public void addItem(String name, Context context) {
        EntityCity entityCity = new EntityCity();
        entityCity.nameCity = name;
        try {
            LatLng coordCity = Tools.getCoordinateCity(name, context);
            entityCity.latitude = coordCity.latitude;
            entityCity.longitude = coordCity.longitude;
        } catch (Exception e) {
            entityCity.latitude = null;
            entityCity.longitude = null;
        }
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
