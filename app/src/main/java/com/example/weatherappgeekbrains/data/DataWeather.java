package com.example.weatherappgeekbrains.data;

import android.content.res.Resources;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.WeatherModel;
import com.example.weatherappgeekbrains.tools.Tools;

import java.util.ArrayList;
import java.util.List;

public class DataWeather implements IDataRecycler {
    private List<WeatherModel> weatherModelList;
    private Resources resources;

    DataWeather(Resources resources) {
        this.weatherModelList = new ArrayList<>();
        this.resources = resources;
    }

    void init() {
        String[] weatherDay = resources.getStringArray(R.array.name_day_week);
        int[] images = Tools.getImageArray(resources, R.array.icons_weather_week);
        String[] temperature = resources.getStringArray(R.array.temperature_day);
        String[] statusWeather = resources.getStringArray(R.array.status_day);
        for (int i = 0; i < weatherDay.length; i++) {
            weatherModelList.add(new WeatherModel(weatherDay[i], statusWeather[i], temperature[i], images[i]));
        }
    }

    @Override
    public <T> T getData(int position) {
        return (T) weatherModelList.get(position);
    }

    @Override
    public int size() {
        return weatherModelList.size();
    }

    @Override
    public Resources getResources() {
        return resources;
    }
}
