package com.example.weatherappgeekbrains.models.ModelGetWeatherFromCor;

import com.example.weatherappgeekbrains.models.WeatherModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;
    @SerializedName("temp")
    @Expose
    private Temperature temp;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("feels_like")
    @Expose
    private FeelsLike feelsLike;
    @Expose
    private List<WeatherModel> weather = null;

    public Integer getDt() {
        return dt;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public Temperature getTemp() {
        return temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public FeelsLike getFeelsLike() {
        return feelsLike;
    }

    public List<WeatherModel> getWeather() {
        return weather;
    }
}

