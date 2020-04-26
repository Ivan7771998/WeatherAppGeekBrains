package com.example.weatherappgeekbrains.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.models.CityData;
import com.example.weatherappgeekbrains.ui.activities.SelectCityActivity;

import java.util.Objects;


public class CitiesFragment extends Fragment {

    private boolean isLandscapeOrientation;
    private CityData currentCityData;
    private String[] nameCity;
    private String[] urlCity;

    public CitiesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initArrays();
        initList((LinearLayout) view);
    }

    private void initArrays() {
        nameCity = getResources().getStringArray(R.array.name_city);
        urlCity = getResources().getStringArray(R.array.url_city_weather);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscapeOrientation = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentCityData = savedInstanceState.getParcelable("city");
        } else {
            currentCityData = new CityData(nameCity[0], 0, urlCity[0]);
        }

        if (isLandscapeOrientation) {
            showCoatOfArms(currentCityData);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("city", currentCityData);
        super.onSaveInstanceState(outState);
    }

    private void initList(@NonNull LinearLayout view) {
        String[] cities = getResources().getStringArray(R.array.name_city);
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            TextView tv = new TextView(getContext());
            tv.setText(city);
            tv.setTextSize(30);
            view.addView(tv);
            final int indexCity = i;
            tv.setOnClickListener(v -> {
                currentCityData = new CityData(nameCity[indexCity], indexCity, urlCity[indexCity]);
                showCoatOfArms(currentCityData);
            });
        }
    }

    private void showCoatOfArms(CityData cityData) {
        if (isLandscapeOrientation) {
            assert getFragmentManager() != null;
            CoatOfArmsFragment detail = (CoatOfArmsFragment)
                    getFragmentManager().findFragmentById(R.id.coat_of_arms);
            if (detail == null || detail.getImage() != cityData.getImageId()) {
                detail = CoatOfArmsFragment.newInstance(cityData);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent(Objects.requireNonNull(getActivity()), SelectCityActivity.class);
            intent.putExtra("city", cityData);
            startActivity(intent);
        }

    }
}
