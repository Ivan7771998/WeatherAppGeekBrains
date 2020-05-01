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
    public Resources getResources() {
        return resources;
    }


    DataCities init() {
        String[] nameCities = resources.getStringArray(R.array.name_city);
        int[] images = Tools.getImageArray(resources, R.array.icons_city);
        String[] urlCities = resources.getStringArray(R.array.url_city_weather);
        for (int i = 0; i < nameCities.length; i++) {
            cityModelList.add(new CityModel(nameCities[i], images[i], urlCities[i]));
        }
        return this;
    }

}
