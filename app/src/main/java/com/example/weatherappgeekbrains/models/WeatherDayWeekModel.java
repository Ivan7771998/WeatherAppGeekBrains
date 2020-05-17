package com.example.weatherappgeekbrains.models;

public class WeatherDayWeekModel {

    private String dayWeek;
    private String statusWeather;
    private String temperature;
    private int idImage;

    public WeatherDayWeekModel(String dayWeek, String statusWeather, String temperature, int idImage) {
        this.dayWeek = dayWeek;
        this.statusWeather = statusWeather;
        this.temperature = temperature;
        this.idImage = idImage;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public String getStatusWeather() {
        return statusWeather;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getIdImage() {
        return idImage;
    }
}
