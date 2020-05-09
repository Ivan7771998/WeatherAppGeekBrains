package com.example.weatherappgeekbrains.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListWeatherWeek;
import com.example.weatherappgeekbrains.data.DataWeatherBuilder;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.CityModel;
import com.example.weatherappgeekbrains.models.CurrentWeatherPojo;
import com.example.weatherappgeekbrains.network.IRetrofitRequests;
import com.example.weatherappgeekbrains.network.RetrofitClientInstance;
import com.example.weatherappgeekbrains.tools.Tools;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CoatOfArmsFragment extends Fragment {

    private static final String CITY_DATA = "city";
    private CityModel cityModel;
    private CurrentWeatherPojo currentWeather;

    @BindView(R.id.btnMoreInfo)
    MaterialButton btnMoreInfo;

    @BindView(R.id.imageCity)
    ImageView imageCity;

    @BindView(R.id.titleWeather)
    TextView titleWeather;

    @BindView(R.id.list_week)
    RecyclerView recyclerView;

    @BindView(R.id.textFeelLike)
    TextView textFeelLike;

    @BindView(R.id.textMinTemp)
    TextView textMinTemp;

    @BindView(R.id.textMaxTemp)
    TextView textMaxTemp;

    @BindView(R.id.textHumidity)
    TextView textHumidity;

    @BindView(R.id.textPressure)
    TextView textPressure;

    @BindView(R.id.textWindSpeed)
    TextView textWindSpeed;

    @BindView(R.id.textSunRise)
    TextView textSunRise;

    @BindView(R.id.textSunSet)
    TextView textSunSet;

    @BindView(R.id.textVisibility)
    TextView textVisibility;

    @BindView(R.id.textTemperature)
    TextView textTemperature;

    @BindView(R.id.textStatusWeather)
    TextView textStatusWeather;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;

    @BindView(R.id.textDayTimeNow)
    TextView textDayTimeNow;

    private Unbinder unbinder;

    public CoatOfArmsFragment() {
    }

    static CoatOfArmsFragment newInstance(CityModel cityModel) {
        CoatOfArmsFragment fragment = new CoatOfArmsFragment();
        Bundle args = new Bundle();
        args.putParcelable(CITY_DATA, cityModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityModel = getArguments().getParcelable(CITY_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coat_of_arms, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        getWeatherData();
        IDataRecycler iDataRecycler = initDataWeather();
        initListWeather(iDataRecycler);
    }

    private void getWeatherData() {
        progressBar.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
        IRetrofitRequests retrofitRequests = RetrofitClientInstance.getRetrofitInstance()
                .create(IRetrofitRequests.class);
        retrofitRequests.getCurrentWeather(cityModel.getNameCity(), "metric", "ru", Tools.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CurrentWeatherPojo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CurrentWeatherPojo currentWeatherPojo) {
                        currentWeather = currentWeatherPojo;
                        progressBar.setVisibility(View.GONE);
                        mainContainer.setVisibility(View.VISIBLE);
                        initView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("REQUEST", e.toString());
                    }
                });
    }

    private void initView() {
        textTemperature.setText(new StringBuilder(
                Double.valueOf(currentWeather.getMain().getTemp().toString()).intValue()
                        + " " +
                        requireActivity().getResources().getString(R.string.temperature_values)));
        String descriptionWeather = currentWeather.getWeather().get(0).getDescription();
        textStatusWeather.setText(new StringBuilder(descriptionWeather.substring(0, 1).toUpperCase() +
                descriptionWeather.substring(1).toLowerCase()));
        textDayTimeNow.setText(new StringBuilder(Tools.getDayWeek(getResources()) +
                " " + Tools.getCurrentTime()));
        textFeelLike.setText(new StringBuilder(Double.valueOf(currentWeather.getMain()
                .getFeelsLike().toString()).intValue()
                + " " +
                requireActivity().getResources().getString(R.string.temperature_values)));
        textMinTemp.setText(new StringBuilder(Double.valueOf(currentWeather.getMain()
                .getTempMin().toString()).intValue()
                + " " +
                requireActivity().getResources().getString(R.string.temperature_values)));
        textMaxTemp.setText(new StringBuilder(Double.valueOf(currentWeather.getMain()
                .getTempMax().toString()).intValue()
                + " " +
                requireActivity().getResources().getString(R.string.temperature_values)));
        textPressure.setText(new StringBuilder(Double.valueOf(currentWeather.getMain()
                .getPressure().toString()).intValue()
                + " " +
                requireActivity().getResources().getString(R.string.pressure_values)));
        textHumidity.setText(new StringBuilder(Double.valueOf(currentWeather.getMain()
                .getHumidity().toString()).intValue()
                + " " +
                requireActivity().getResources().getString(R.string.humidity_values)));
        textWindSpeed.setText(new StringBuilder(Double.valueOf(currentWeather.getWind()
                .getSpeed().toString()).longValue()
                + " " +
                requireActivity().getResources().getString(R.string.wind_values)));
        textSunRise.setText(Tools.getTimeFromMil(Long.valueOf(currentWeather.getSys().getSunrise())));
        textSunSet.setText(Tools.getTimeFromMil(Long.valueOf(currentWeather.getSys().getSunset())));
        textVisibility.setText(new StringBuilder(currentWeather.getVisibility() / 1000 + " "
                + requireActivity().getResources().getString(R.string.visible_values)));
        titleWeather.setText(cityModel.getNameCity());
        imageCity.setImageDrawable(requireActivity().getResources()
                .getDrawable(cityModel.getImageId()));
        btnMoreInfo.setOnClickListener(v -> {
            startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(cityModel.getUrlCity())));
        });
    }

    private IDataRecycler initDataWeather() {
        return new DataWeatherBuilder()
                .setResources(getResources())
                .build();
    }

    private void initListWeather(IDataRecycler iDataRecycler) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AdapterListWeatherWeek adapterListWeatherWeek = new AdapterListWeatherWeek(iDataRecycler);
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(requireContext(),
                        LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapterListWeatherWeek);
    }

    int getImage() {
        return getCityModel().getImageId();
    }

    private CityModel getCityModel() {
        assert getArguments() != null;
        return getArguments().getParcelable(CITY_DATA);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
