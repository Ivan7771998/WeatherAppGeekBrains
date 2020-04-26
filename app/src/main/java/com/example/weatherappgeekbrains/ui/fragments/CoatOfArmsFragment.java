package com.example.weatherappgeekbrains.ui.fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.models.CityData;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CoatOfArmsFragment extends Fragment {

    private static final String CITY_DATA = "city";
    private CityData cityData;
    private TypedArray imagesCity;
    @BindView(R.id.showPrecipitation)
    CheckBox showPrecipitation;
    @BindView(R.id.showHumidity)
    CheckBox showHumidity;
    @BindView(R.id.showWind)
    CheckBox showWind;
    @BindView(R.id.textPrecipitation)
    TextView textPrecipitation;
    @BindView(R.id.textHumidity)
    TextView textHumidity;
    @BindView(R.id.textWind)
    TextView textWind;
    @BindView(R.id.btnMoreInfo)
    MaterialButton btnMoreInfo;
    @BindView(R.id.imageCity)
    ImageView imageCity;
    @BindView(R.id.titleWeather)
    TextView titleWeather;

    private Unbinder unbinder;

    public CoatOfArmsFragment() {
    }

    static CoatOfArmsFragment newInstance(CityData cityData) {
        CoatOfArmsFragment fragment = new CoatOfArmsFragment();
        Bundle args = new Bundle();
        args.putParcelable(CITY_DATA, cityData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityData = getArguments().getParcelable(CITY_DATA);
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
        imagesCity = getResources().obtainTypedArray(R.array.icons_city);
        initWorkViewCheckBox();
    }

    private void initWorkViewCheckBox() {
        showPrecipitation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkVisibility(textPrecipitation, isChecked);
        });

        showHumidity.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkVisibility(textHumidity, isChecked);
        });

        showWind.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkVisibility(textWind, isChecked);
        });
        titleWeather.setText(cityData.getNameCity());
        imageCity.setImageDrawable(imagesCity.getDrawable(cityData.getImageId()));
        btnMoreInfo.setOnClickListener(v -> {
            startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(cityData.getUrlCity())));
        });
    }

    int getImage() {
        return getCityData().getImageId();
    }

    private CityData getCityData(){
        assert getArguments() != null;
        return getArguments().getParcelable(CITY_DATA);
    }

    private void checkVisibility(TextView text, boolean isChecked) {
        if (isChecked) {
            text.setVisibility(View.VISIBLE);
        } else {
            text.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
