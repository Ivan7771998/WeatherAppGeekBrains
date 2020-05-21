package com.example.weatherappgeekbrains.data;

import android.content.res.Resources;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.CityModel;
import com.example.weatherappgeekbrains.tools.Tools;

import java.util.ArrayList;
import java.util.List;

public class DataCities implements IDataRecycler {

    private List<CityModel> cityModelList;
    private Resources resources;
    private int[] images;
    private String[] urlCities;


    DataCities(Resources resources) {
        this.cityModelList = new ArrayList<>();
        this.resources = resources;
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
    public void addCity(String name) {
        cityModelList.add(new CityModel(name,  images[0], urlCities[0]));
    }

    @Override
    public void removeCity(int position) {
        cityModelList.remove(position);
    }

    @Override
    public Resources getResources() {
        return resources;
    }

    void init() {
        String[] nameCities = resources.getStringArray(R.array.name_city);
        images = Tools.getImageArray(resources, R.array.icons_city);
        urlCities = resources.getStringArray(R.array.url_city_weather);
        for (int i = 0; i < nameCities.length; i++) {
            cityModelList.add(new CityModel(nameCities[i], images[i], urlCities[i]));
        }
    }
}
