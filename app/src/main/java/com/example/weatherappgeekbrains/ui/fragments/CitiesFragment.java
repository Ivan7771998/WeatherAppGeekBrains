package com.example.weatherappgeekbrains.ui.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListNameCity;
import com.example.weatherappgeekbrains.data.DataCitiesBuilder;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.CityModel;
import com.example.weatherappgeekbrains.ui.activities.MainActivity;
import com.example.weatherappgeekbrains.ui.activities.SelectCityActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CitiesFragment extends Fragment {

    private boolean isLandscapeOrientation;
    private CityModel currentCityModel;
    private IDataRecycler iDataRecycler;

    @BindView(R.id.list_cities)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        iDataRecycler = initDataCities();
        initListCities(iDataRecycler);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscapeOrientation = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentCityModel = savedInstanceState.getParcelable("city");
        } else {
            CityModel cityModel = iDataRecycler.getData(0);
            currentCityModel = new CityModel(cityModel.getNameCity(), cityModel.getImageId(),
                    cityModel.getUrlCity());
        }

        if (isLandscapeOrientation) {
            showCoatOfArms(currentCityModel);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("city", currentCityModel);
        super.onSaveInstanceState(outState);
    }

    private IDataRecycler initDataCities() {
        return new DataCitiesBuilder()
                .setResources(getResources())
                .build();
    }

    private void initListCities(IDataRecycler iDataRecycler) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        AdapterListNameCity adapterListNameCity = new AdapterListNameCity(iDataRecycler);
        recyclerView.setAdapter(adapterListNameCity);

        adapterListNameCity.setOnItemClickListener((view, position) -> {
            currentCityModel = iDataRecycler.getData(position);
            showCoatOfArms(currentCityModel);
        });
    }

    private void showCoatOfArms(CityModel cityModel) {
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        if (isLandscapeOrientation) {
            CoatOfArmsFragment detail = (CoatOfArmsFragment)
                    mainActivity.getSupportFragmentManager().findFragmentById(R.id.coat_of_arms);
            if (detail == null || detail.getImage() != cityModel.getImageId()) {
                detail = CoatOfArmsFragment.newInstance(cityModel);
                FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent(requireActivity(), SelectCityActivity.class);
            intent.putExtra("city", cityModel);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
