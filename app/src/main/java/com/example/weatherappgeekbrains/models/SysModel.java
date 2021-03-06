package com.example.weatherappgeekbrains.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SysModel {

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;

    @SerializedName("sunset")
    @Expose
    private Integer sunset;

    public Integer getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }
}
