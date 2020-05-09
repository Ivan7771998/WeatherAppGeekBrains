package com.example.weatherappgeekbrains.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherPojo {

    @SerializedName("coord")
    @Expose
    private CoordPojo coord;

    @SerializedName("weather")
    @Expose
    private List<WeatherPojo> weather = null;

    @SerializedName("base")
    @Expose
    private String base;

    @SerializedName("main")
    @Expose
    private MainPojo main;

    @SerializedName("visibility")
    @Expose
    private Integer visibility = 10000;

    @SerializedName("wind")
    @Expose
    private WindPojo wind;

    @SerializedName("clouds")
    @Expose
    private CloudsPojo clouds;

    @SerializedName("dt")
    @Expose
    private Integer dt;

    @SerializedName("sys")
    @Expose
    private SysPojo sys;

    @SerializedName("timezone")
    @Expose
    private Integer timezone;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("cod")
    @Expose
    private Integer cod;

    public CoordPojo getCoord() {
        return coord;
    }

    public List<WeatherPojo> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public MainPojo getMain() {
        return main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public WindPojo getWind() {
        return wind;
    }

    public CloudsPojo getClouds() {
        return clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public SysPojo getSys() {
        return sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return "CurrentWeatherPojo{" +
                "coord=" + coord.toString() +
                ", weather=" + weather.get(0).toString() +
                ", base='" + base + '\'' +
                ", main=" + main.toString() +
                ", visibility=" + visibility +
                ", wind=" + wind.toString() +
                ", clouds=" + clouds.toString() +
                ", dt=" + dt +
                ", sys=" + sys.toString() +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}
