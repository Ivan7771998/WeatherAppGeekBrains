package com.example.weatherappgeekbrains.network;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.models.CurrentWeatherModel;
import com.example.weatherappgeekbrains.models.ModelGetWeatherFromCor.DataWeatherFromCor;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.Tools;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    public void getWeatherData(IAnswerRequest iAnswerRequest, ProgressBar progressBar, ConstraintLayout mainContainer,
                               String cityName) {
        progressBar.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
        IRetrofitRequests retrofitRequests = RetrofitClientInstance.getRetrofitInstance()
                .create(IRetrofitRequests.class);
        retrofitRequests.getCurrentWeather(cityName, "metric", "ru",
                Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CurrentWeatherModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CurrentWeatherModel currentWeatherModel) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            mainContainer.setVisibility(View.VISIBLE);
                            iAnswerRequest.onSuccess(currentWeatherModel);
                        } catch (Exception e) {
                            Log.e("TAG", "fragment onDetach()");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("REQUEST", e.toString());
                        iAnswerRequest.onError(e);
                    }
                });
    }

    public void getWeatherFromCoordinate(IAnswerRequestFromCor answerRequestFromCor,
                                         EntityCity currentCity) {
        IRetrofitRequests retrofitRequests = RetrofitClientInstance.getRetrofitInstance()
                .create(IRetrofitRequests.class);
        retrofitRequests.getCurrentWeatherAndWeek(String.valueOf(currentCity.latitude),
                String.valueOf(currentCity.longitude), "minutely", "metric", "ru",
                Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DataWeatherFromCor>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(DataWeatherFromCor dataWeatherFromCor) {
                        answerRequestFromCor.onSuccess(dataWeatherFromCor);
                    }

                    @Override
                    public void onError(Throwable e) {
                        answerRequestFromCor.onError(e);
                    }
                });
    }

    public interface IAnswerRequestFromCor {
        void onSuccess(DataWeatherFromCor dataWeatherFromCor);

        void onError(Throwable e);
    }

    public interface IAnswerRequest {
        void onSuccess(CurrentWeatherModel currentWeatherModel);

        void onError(Throwable e);
    }
}
