package com.example.weatherappgeekbrains.interfaces;

import android.content.res.Resources;

public interface IDataRecycler {
    <T> T getData(int position);
    int size();
    Resources getResources();
}
