package com.example.weatherappgeekbrains.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CityData implements Parcelable {
    private String nameCity;
    private int imageCityId;
    private String urlCity;

    public CityData(String nameCity, int imageCityId, String urlCity) {
        this.nameCity = nameCity;
        this.imageCityId = imageCityId;
        this.urlCity = urlCity;
    }

    private CityData(Parcel in) {
        nameCity = in.readString();
        urlCity = in.readString();
        imageCityId = in.readInt();
    }

    public static final Creator<CityData> CREATOR = new Creator<CityData>() {
        @Override
        public CityData createFromParcel(Parcel in) {
            return new CityData(in);
        }

        @Override
        public CityData[] newArray(int size) {
            return new CityData[size];
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
