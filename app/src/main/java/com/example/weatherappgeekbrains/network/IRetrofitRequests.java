package com.example.weatherappgeekbrains.network;

import com.example.weatherappgeekbrains.models.CurrentWeatherModel;
import com.example.weatherappgeekbrains.models.ModelGetWeatherFromCor.DataWeatherFromCor;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRetrofitRequests {

    @GET("weather?")
    Single<CurrentWeatherModel> getCurrentWeather(
            @Query("q") String nameCity,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String apiKey);

    @GET("onecall?")
    Single<DataWeatherFromCor> getCurrentWeatherAndWeek(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("exclude") String exclude,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String apiKey);
}
