package com.example.weatherappgeekbrains.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoordModel {

    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }
}
