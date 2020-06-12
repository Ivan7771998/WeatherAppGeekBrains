package com.example.weatherappgeekbrains.network;

import android.util.Log;

import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.models.CurrentWeatherModel;
import com.example.weatherappgeekbrains.models.ModelGetWeatherFromCor.DataWeatherFromCor;
import com.example.weatherappgeekbrains.tools.Constants;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    private void getWeatherData(DataWeatherFromCor dataWeatherFromCor,
                                IAnswerRequestFromCor answerRequestFromCor,
                                EntityCity currentCity) {
        IRetrofitRequests retrofitRequests = RetrofitClientInstance.getRetrofitInstance()
                .create(IRetrofitRequests.class);
        retrofitRequests.getCurrentWeather(currentCity.latitude.toString(),
                currentCity.longitude.toString(), "metric", "ru",
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
                            answerRequestFromCor.onSuccess(dataWeatherFromCor, currentWeatherModel);
                        } catch (Exception e) {
                            Log.e("TAG", "fragment onDetach()");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("REQUEST", e.toString());
                        answerRequestFromCor.onError(e);
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
                        getWeatherData(dataWeatherFromCor, answerRequestFromCor, currentCity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        answerRequestFromCor.onError(e);
                    }
                });
    }

    public interface IAnswerRequestFromCor {
        void onSuccess(DataWeatherFromCor dataWeatherFromCor, CurrentWeatherModel currentWeatherModel);

        void onError(Throwable e);
    }
}
