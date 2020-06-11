package com.example.weatherappgeekbrains.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.weatherappgeekbrains.App;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListWeatherDays;
import com.example.weatherappgeekbrains.adaters.AdapterListWeatherHours;
import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.database.entities.EntityWeatherDesc;
import com.example.weatherappgeekbrains.models.CurrentWeatherModel;
import com.example.weatherappgeekbrains.models.ModelGetWeatherFromCor.DataWeatherFromCor;
import com.example.weatherappgeekbrains.network.Repository;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.Tools;
import com.example.weatherappgeekbrains.tools.UntilTimes;
import com.example.weatherappgeekbrains.ui.dialogs.DialogErrorWithCity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CoatOfArmsFragment extends Fragment {

    static final String CITY_DATA_FR = "city_fragment";
    static final String CITY_DATA_HISTORY = "city_history_fragment";
    public static final String CITY_DATA_LAT = "city_data_lat";
    public static final String CITY_DATA_LNG = "city_data_lng";
    static final String FIND_WEATHER_MY_LOCATION = "find_weather_my_location";
    static final String TAG = "CoatOfArmsFragment";
    private long idCity;
    private EntityCity currentCity;
    private DataWeatherFromCor weatherFromCor;
    private CurrentWeatherModel currentWeatherCor;

    @BindView(R.id.btnMoreInfo)
    MaterialButton btnMoreInfo;

    @BindView(R.id.titleWeather)
    TextView titleWeather;

    @BindView(R.id.list_weather_in_days)
    RecyclerView recyclerViewWeatherInDays;

    @BindView(R.id.list_weather_in_hours)
    RecyclerView recyclerViewWeatherInHours;

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

    @BindView(R.id.txtSelectDays)
    TextView txtSelectDays;

    @BindView(R.id.txtSelectHours)
    TextView txtSelectHours;

    @BindView(R.id.subTitleWeather)
    TextView subTitleWeather;

    @BindView(R.id.imgWeatherFr)
    ImageView imgWeatherFr;


    private Unbinder unbinder;

    public CoatOfArmsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(CITY_DATA_FR)) {
                idCity = getArguments().getLong(CITY_DATA_FR);
                currentCity = App.getInstance().getCityDao().getCityById(idCity);
            } else if (getArguments().containsKey(CITY_DATA_HISTORY)) {
                currentCity = App.getInstance()
                        .getCityDao().getCityByName(getArguments().getString(CITY_DATA_HISTORY));
            } else if (getArguments().containsKey(CITY_DATA_LAT)) {
                currentCity = new EntityCity();
                LatLng coordinate = new LatLng(getArguments().getDouble(CITY_DATA_LAT),
                        getArguments().getDouble(CITY_DATA_LNG));
                currentCity.latitude = coordinate.latitude;
                currentCity.longitude = coordinate.longitude;
                currentCity.nameCity = null;
                currentCity.id = 0;
            }
            //MySharedPref.setCurrentCity(currentCity.nameCity);
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
            getWeather();
            onClickPrevView();
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
    }

    private void getWeather() {
        progressBar.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
        App.getInstance().getRepository().getWeatherFromCoordinate(new Repository.IAnswerRequestFromCor() {
            @Override
            public void onSuccess(DataWeatherFromCor dataWeatherFromCor, CurrentWeatherModel currentWeatherModel) {
                Log.e("TAG", dataWeatherFromCor.toString());
                progressBar.setVisibility(View.GONE);
                mainContainer.setVisibility(View.VISIBLE);
                weatherFromCor = dataWeatherFromCor;
                currentWeatherCor = currentWeatherModel;
                addInDB();
                initView();
                initListWeather();
            }

            @Override
            public void onError(Throwable e) {
                showDialogError();
            }
        }, currentCity);
    }

    private void addInDB() {
        if (currentCity.id != 0) {
            EntityWeatherDesc entityWeatherDesc = new EntityWeatherDesc();
            entityWeatherDesc.txtTemperature = Double.valueOf(weatherFromCor.getCurrent().temp.toString()).intValue()
                    + " " + requireActivity().getResources().getString(R.string.temperature_values);
            entityWeatherDesc.entityCityId = currentCity.id;
            String descriptionWeather = weatherFromCor.getCurrent().weather.get(0).getDescription();
            entityWeatherDesc.txtDescription = descriptionWeather.substring(0, 1).toUpperCase() +
                    descriptionWeather.substring(1).toLowerCase();
            entityWeatherDesc.txtDate = (Tools.getDayWeek(getResources()) +
                    ", " + UntilTimes.getAllDate());
            entityWeatherDesc.txtImg = weatherFromCor.getCurrent().weather.get(0).getIcon();
            App.getInstance().getCityDao().insertWeatherDesc(entityWeatherDesc);
        }
    }

    private void onClickPrevView() {
        prevLayout.setOnClickListener(v -> workWithPrevView());
        txtSelectHours.setOnClickListener(v -> selectDaysOrHoursWeather(txtSelectHours));
        txtSelectDays.setOnClickListener(v -> selectDaysOrHoursWeather(txtSelectDays));
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
            if (getContext() != null)
                Glide.with(getContext())
                        .load(Constants.GET_IMG + weatherFromCor.getCurrent().getWeather().get(0).getIcon() + ".png")
                        .centerCrop()
                        .into(imgWeatherFr);
            textTemperature.setText(new StringBuilder(
                    Double.valueOf(weatherFromCor.getCurrent().getTemp().toString()).intValue()
                            + " " +
                            requireActivity().getResources().getString(R.string.temperature_values)));
            String descriptionWeather = weatherFromCor.getCurrent().getWeather().get(0).getDescription();
            textStatusWeather.setText(new StringBuilder(descriptionWeather.substring(0, 1).toUpperCase() +
                    descriptionWeather.substring(1).toLowerCase()));
            textDayTimeNow.setText(new StringBuilder(Tools.getDayWeek(getResources()) +
                    " " + UntilTimes.getCurrentTime()));
            textFeelLike.setText(new StringBuilder(Double.valueOf(weatherFromCor.getCurrent()
                    .getFeelsLike().toString()).intValue()
                    + " " +
                    requireActivity().getResources().getString(R.string.temperature_values)));
            textMinTemp.setText(new StringBuilder(Double.valueOf(currentWeatherCor.getMain()
                    .getTempMin().toString()).intValue()
                    + " " +
                    requireActivity().getResources().getString(R.string.temperature_values)));
            textMaxTemp.setText(new StringBuilder(Double.valueOf(currentWeatherCor.getMain()
                    .getTempMax().toString()).intValue()
                    + " " +
                    requireActivity().getResources().getString(R.string.temperature_values)));
            textPressure.setText(new StringBuilder(Double.valueOf(weatherFromCor.getCurrent()
                    .getPressure().toString()).intValue()
                    + " " +
                    requireActivity().getResources().getString(R.string.pressure_values)));
            textHumidity.setText(new StringBuilder(Double.valueOf(weatherFromCor.getCurrent()
                    .getHumidity().toString()).intValue()
                    + " " +
                    requireActivity().getResources().getString(R.string.humidity_values)));
            textWindSpeed.setText(new StringBuilder(Double.valueOf(weatherFromCor.getCurrent().windSpeed
                    .toString()).longValue()
                    + " " +
                    requireActivity().getResources().getString(R.string.wind_values)));
            textSunRise.setText(UntilTimes.getTimeFromMil(Long.valueOf(weatherFromCor.getCurrent().getSunrise())));
            textSunSet.setText(UntilTimes.getTimeFromMil(Long.valueOf(weatherFromCor.getCurrent().getSunset())));
            textVisibility.setText(new StringBuilder(weatherFromCor.getCurrent().getVisibility() / 1000 + " "
                    + requireActivity().getResources().getString(R.string.visible_values)));

            titleWeather.setText(currentWeatherCor.getName());
            Tools.setAddress(new LatLng(currentCity.latitude, currentCity.longitude), getContext(), subTitleWeather);
            openWeatherInBrowser();
        } catch (Exception e) {
            Log.e("TAG", "fragment onDetach()");
        }
    }

    private void openWeatherInBrowser() {
        btnMoreInfo.setOnClickListener(v -> {
            startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.meteonova.ru/frcgeo/?fi=" +
                            currentCity.latitude +
                            "&la=" +
                            currentCity.longitude +
                            "&title=" +
                            subTitleWeather.getText())));
        });
    }

    private void initListWeather() {
        recyclerViewWeatherInDays.setHasFixedSize(true);
        recyclerViewWeatherInHours.setHasFixedSize(true);
        LinearLayoutManager layoutManagerForDays = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManagerForHours = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        recyclerViewWeatherInDays.setLayoutManager(layoutManagerForDays);
        recyclerViewWeatherInHours.setLayoutManager(layoutManagerForHours);
        AdapterListWeatherDays adapterListWeatherDays = new AdapterListWeatherDays(weatherFromCor, getContext());
        AdapterListWeatherHours adapterListWeatherHours = new AdapterListWeatherHours(weatherFromCor, getContext());
        recyclerViewWeatherInDays.setAdapter(adapterListWeatherDays);
        recyclerViewWeatherInHours.setAdapter(adapterListWeatherHours);
    }

    @SuppressLint("ResourceAsColor")
    private void selectDaysOrHoursWeather(TextView selectedView) {
        txtSelectDays.setBackgroundColor(android.R.color.white);
        txtSelectHours.setBackgroundColor(android.R.color.white);
        if (getActivity() != null)
            selectedView.setBackground(getActivity().getDrawable(R.drawable.select_days_or_hours));
        if (txtSelectDays == selectedView) {
            recyclerViewWeatherInDays.setVisibility(View.VISIBLE);
            recyclerViewWeatherInHours.setVisibility(View.GONE);
        } else {
            recyclerViewWeatherInDays.setVisibility(View.GONE);
            recyclerViewWeatherInHours.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    private void showDialogError() {
        try {
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            DialogErrorWithCity dialogErrorWithCity = DialogErrorWithCity.newInstance();
            dialogErrorWithCity.setCancelable(false);
            dialogErrorWithCity.show(ft, "showDialogError");
        } catch (Exception e) {
            Log.e(TAG, "view onDetach()");
        }
    }

}
