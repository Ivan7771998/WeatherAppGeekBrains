package com.example.weatherappgeekbrains.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloudsModel {

    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }
}
