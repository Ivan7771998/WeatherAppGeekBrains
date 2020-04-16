package com.example.weatherappgeekbrains;

import android.view.View;

public final class MainPresenter {

    private static MainPresenter instance = null;
    private static final Object syncObj = new Object();

    private int showPrecipitation;
    private int showHumidity;
    private int showWind;

    private MainPresenter() {
        showPrecipitation = View.VISIBLE;
        showHumidity = View.VISIBLE;
        showWind = View.VISIBLE;
    }

    public int getShowPrecipitation() {
        return showPrecipitation;
    }

    public void setShowPrecipitation(int showPrecipitation) {
        this.showPrecipitation = showPrecipitation;
    }

    public int getShowHumidity() {
        return showHumidity;
    }

    public void setShowHumidity(int showHumidity) {
        this.showHumidity = showHumidity;
    }

    public int getShowWind() {
        return showWind;
    }

    public void setShowWind(int showWind) {
        this.showWind = showWind;
    }

    public static MainPresenter getInstance() {
        synchronized (syncObj) {
            if (instance == null) {
                instance = new MainPresenter();
            }
            return instance;
        }
    }
}
