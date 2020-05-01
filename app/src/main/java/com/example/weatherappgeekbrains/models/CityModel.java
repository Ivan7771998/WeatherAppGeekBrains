package com.example.weatherappgeekbrains.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CityModel implements Parcelable {
    private String nameCity;
    private int imageCityId;
    private String urlCity;

    public CityModel(String nameCity, int imageCityId, String urlCity) {
        this.nameCity = nameCity;
        this.imageCityId = imageCityId;
        this.urlCity = urlCity;
    }

    private CityModel(Parcel in) {
        nameCity = in.readString();
        urlCity = in.readString();
        imageCityId = in.readInt();
    }

    public static final Creator<CityModel> CREATOR = new Creator<CityModel>() {
        @Override
        public CityModel createFromParcel(Parcel in) {
            return new CityModel(in);
        }

        @Override
        public CityModel[] newArray(int size) {
            return new CityModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nameCity);
        dest.writeString(urlCity);
        dest.writeInt(imageCityId);
    }

    public String getNameCity() {
        return nameCity;
    }

    public int getImageId() {
        return imageCityId;
    }

    public String getUrlCity() {
        return urlCity;
    }
}
