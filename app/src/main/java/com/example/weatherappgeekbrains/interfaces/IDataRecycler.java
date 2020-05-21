package com.example.weatherappgeekbrains.interfaces;

import android.content.res.Resources;

public interface IDataRecycler {
    <T> T getData(int position);
    int size();
    void addCity(String nameCity);
    void removeCity(int position);
    Resources getResources();
}
