package com.example.weatherappgeekbrains.interfaces;

public interface IDataHourWeather {
    <T> T getData(int position);
    int size();
    void addCity(String nameCity);
}
