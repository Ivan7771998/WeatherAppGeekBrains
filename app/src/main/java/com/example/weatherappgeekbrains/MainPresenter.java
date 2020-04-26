package com.example.weatherappgeekbrains;

import android.view.View;

public final class MainPresenter {

    private static MainPresenter instance = null;
    private static final Object syncObj = new Object();

    private String titleWeather;
    private int showCheckView;
    private String textCheckView;

    private MainPresenter() {
        showCheckView = View.GONE;
        textCheckView = "";
    }

    int getShowCheckView() {
        return showCheckView;
    }

    void setShowCheckView(int showCheckView) {
        this.showCheckView = showCheckView;
    }

    String getTextCheckView() {
        return textCheckView;
    }

    void setTextCheckView(String textCheckView) {
        this.textCheckView = textCheckView;
    }

    public String getTitleWeather() {
        return titleWeather;
    }

    public void setTitleWeather(String titleWeather) {
        this.titleWeather = titleWeather;
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
