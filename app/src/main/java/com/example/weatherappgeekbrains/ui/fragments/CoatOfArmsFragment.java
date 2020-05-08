package com.example.weatherappgeekbrains.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListWeatherWeek;
import com.example.weatherappgeekbrains.data.DataWeatherBuilder;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.CityModel;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CoatOfArmsFragment extends Fragment {

    private static final String CITY_DATA = "city";
    private CityModel cityModel;

    @BindView(R.id.btnMoreInfo)
    MaterialButton btnMoreInfo;

    @BindView(R.id.imageCity)
    ImageView imageCity;

    @BindView(R.id.titleWeather)
    TextView titleWeather;

    @BindView(R.id.list_week)
    RecyclerView recyclerView;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView();
        IDataRecycler iDataRecycler = initDataWeather();
        initListWeather(iDataRecycler);
    }

    private void initView() {
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
                new DividerItemDecoration(Objects.requireNonNull(getContext()),
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
