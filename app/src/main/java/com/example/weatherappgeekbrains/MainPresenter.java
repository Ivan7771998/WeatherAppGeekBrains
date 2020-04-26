package com.example.weatherappgeekbrains;

import android.view.View;

public final class MainPresenter {

    private static MainPresenter instance = null;
    private static final Object syncObj = new Object();


    private int showCheckView;
    private String textCheckView;

    private MainPresenter() {
        showCheckView = View.GONE;
        textCheckView = "";
    }

    public int getShowCheckView() {
        return showCheckView;
    }

    public void setShowCheckView(int showCheckView) {
        this.showCheckView = showCheckView;
    }

    public String getTextCheckView() {
        return textCheckView;
    }

    public void setTextCheckView(String textCheckView) {
        this.textCheckView = textCheckView;
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
