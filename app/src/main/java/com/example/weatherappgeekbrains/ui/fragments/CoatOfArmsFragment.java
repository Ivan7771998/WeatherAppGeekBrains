package com.example.weatherappgeekbrains.ui.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherappgeekbrains.App;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListWeatherWeek;
import com.example.weatherappgeekbrains.data.DataWeatherBuilder;
import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.CityModel;
import com.example.weatherappgeekbrains.models.CurrentWeatherModel;
import com.example.weatherappgeekbrains.models.newModel.NewMain;
import com.example.weatherappgeekbrains.network.IRetrofitRequests;
import com.example.weatherappgeekbrains.network.RetrofitClientInstance;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.Tools;
import com.example.weatherappgeekbrains.tools.UntilTimes;
import com.example.weatherappgeekbrains.ui.dialogs.DialogErrorWithCity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CoatOfArmsFragment extends Fragment {

    static final String CITY_DATA = "city";
    private long idCity;
    private EntityCity currentCity;
    private CurrentWeatherModel currentWeather;

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

    @BindView(R.id.prevLayout)
    LinearLayout prevLayout;

    @BindView(R.id.moreInfoLayout)
    LinearLayout moreInfoLayout;

    @BindView(R.id.txtMoreInfo)
    TextView txtMoreInfo;

    @BindView(R.id.txtHideInfo)
    TextView txtHideInfo;

    @BindView(R.id.imgPrevView)
    ImageView imgPrevView;

    private Unbinder unbinder;

    public CoatOfArmsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCity = getArguments().getLong(CITY_DATA);
            currentCity = App.getInstance().getCityDao().getCityById(idCity);
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
        try {
            getWeatherData();
            getWeatherFromCoordinate();
            initListWeather(initDataWeather());
            onClickPrevView();
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
    }

    private void getWeatherData() {
        progressBar.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
        IRetrofitRequests retrofitRequests = RetrofitClientInstance.getRetrofitInstance()
                .create(IRetrofitRequests.class);
        retrofitRequests.getCurrentWeather(currentCity.nameCity, "metric", "ru",
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
                            currentWeather = currentWeatherModel;
                            progressBar.setVisibility(View.GONE);
                            mainContainer.setVisibility(View.VISIBLE);
                            initView();
                        } catch (Exception e) {
                            Log.e("TAG", "fragment onDetach()");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("REQUEST", e.toString());
                        showDialogError();
                    }
                });
    }

    private void getWeatherFromCoordinate() {
        IRetrofitRequests retrofitRequests = RetrofitClientInstance.getRetrofitInstance()
                .create(IRetrofitRequests.class);
        LatLng coordCity = getCoordinateCity(currentCity.nameCity);
        retrofitRequests.getCurrentWeatherAndWeek(String.valueOf(coordCity.latitude),
                String.valueOf(coordCity.longitude), "metric", "ru",
                Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NewMain>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NewMain newMain) {
                        Log.e("TAG", newMain.toString());
                        System.out.println();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    private LatLng getCoordinateCity(String nameCity) {
        LatLng ll = null;
        if (Geocoder.isPresent()) {
            try {
                Geocoder gc = new Geocoder(getContext());
                List<Address> addresses = gc.getFromLocationName(nameCity, 1);
                for (Address a : addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll = new LatLng(a.getLatitude(), a.getLongitude());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ll;
    }

    private void showDialogError() {
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        DialogErrorWithCity dialogErrorWithCity = DialogErrorWithCity.newInstance();
        dialogErrorWithCity.setCancelable(false);
        dialogErrorWithCity.show(ft, "showDialogError");
    }

    private void onClickPrevView() {
        prevLayout.setOnClickListener(v -> workWithPrevView());
    }

    private void workWithPrevView() {
        if (txtMoreInfo.getVisibility() == View.VISIBLE) {
            moreInfoLayout.setVisibility(View.VISIBLE);
            txtMoreInfo.setVisibility(View.INVISIBLE);
            txtHideInfo.setVisibility(View.VISIBLE);
            assert getActivity() != null;
            imgPrevView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_expand_less_24dp));
        } else {
            moreInfoLayout.setVisibility(View.GONE);
            txtMoreInfo.setVisibility(View.VISIBLE);
            txtHideInfo.setVisibility(View.INVISIBLE);
            assert getActivity() != null;
            imgPrevView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_expand_more_24dp));
        }
    }

    private void initView() {
        try {
            textTemperature.setText(new StringBuilder(
                    Double.valueOf(currentWeather.getMain().getTemp().toString()).intValue()
                            + " " +
                            requireActivity().getResources().getString(R.string.temperature_values)));
            String descriptionWeather = currentWeather.getWeather().get(0).getDescription();
            textStatusWeather.setText(new StringBuilder(descriptionWeather.substring(0, 1).toUpperCase() +
                    descriptionWeather.substring(1).toLowerCase()));
            textDayTimeNow.setText(new StringBuilder(Tools.getDayWeek(getResources()) +
                    " " + UntilTimes.getCurrentTime()));
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
            textSunRise.setText(UntilTimes.getTimeFromMil(Long.valueOf(currentWeather.getSys().getSunrise())));
            textSunSet.setText(UntilTimes.getTimeFromMil(Long.valueOf(currentWeather.getSys().getSunset())));
            textVisibility.setText(new StringBuilder(currentWeather.getVisibility() / 1000 + " "
                    + requireActivity().getResources().getString(R.string.visible_values)));
            titleWeather.setText(currentCity.nameCity);
            btnMoreInfo.setOnClickListener(v -> {
//                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse(cityModel.getUrlCity())));
            });
        } catch (Exception e) {
            Log.e("TAG", "fragment onDetach()");
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }
}
