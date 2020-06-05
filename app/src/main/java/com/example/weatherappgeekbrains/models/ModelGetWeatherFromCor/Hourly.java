package com.example.weatherappgeekbrains.models.ModelGetWeatherFromCor;

import com.example.weatherappgeekbrains.models.WeatherModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hourly {

    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("temp")
    @Expose
    public Double temp;
    @SerializedName("feels_like")
    @Expose
    public Double feelsLike;
    @SerializedName("pressure")
    @Expose
    public Integer pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("dew_point")
    @Expose
    public Double dewPoint;
    @SerializedName("clouds")
    @Expose
    public Integer clouds;
    @SerializedName("wind_speed")
    @Expose
    public Double windSpeed;
    @SerializedName("wind_deg")
    @Expose
    public Integer windDeg;
    @SerializedName("weather")
    @Expose
    public List<WeatherModel> weather = null;

    @Override
    public String toString() {
        return "Hourly{" +
                "dt=" + dt +
                ", temp=" + temp +
                ", feelsLike=" + feelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dewPoint=" + dewPoint +
                ", clouds=" + clouds +
                ", windSpeed=" + windSpeed +
                ", windDeg=" + windDeg +
                ", weather=" + weather +
                '}';
    }
}
