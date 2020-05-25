package com.example.weatherappgeekbrains.models.newModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewMain {
    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("timezone")
    @Expose
    public String timezone;
    @SerializedName("timezone_offset")
    @Expose
    public Integer timezoneOffset;
    @SerializedName("current")
    @Expose
    public Current current;
    @SerializedName("hourly")
    @Expose
    public List<Hourly> hourly = null;

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    @Override
    public String toString() {
        return "NewMain{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", timezoneOffset=" + timezoneOffset +
                ", current=" + current +
                ", hourly=" + hourly +
                '}';
    }
}
