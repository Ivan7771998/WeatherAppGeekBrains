package com.example.weatherappgeekbrains.ui.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListNameCity;
import com.example.weatherappgeekbrains.data.DataCitiesBuilder;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.CityModel;
import com.example.weatherappgeekbrains.ui.activities.MainActivity;
import com.example.weatherappgeekbrains.ui.activities.SelectCityActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CitiesFragment extends Fragment {

    private CityModel currentCityModel;
    private IDataRecycler iDataRecycler;

    @BindView(R.id.list_cities)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Unbinder unbinder;
    private AdapterListNameCity adapterListNameCity;

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
        initFAB();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentCityModel = savedInstanceState.getParcelable("city");
        } else {
            CityModel cityModel = iDataRecycler.getData(0);
            currentCityModel = new CityModel(cityModel.getNameCity(), cityModel.getImageId(),
                    cityModel.getUrlCity());
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

    private void initFAB() {
        fab.setOnClickListener(v -> {
            createAlertDialog();
        });
    }

    private void initListCities(IDataRecycler iDataRecycler) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapterListNameCity = new AdapterListNameCity(iDataRecycler, this);
        recyclerView.setAdapter(adapterListNameCity);

        adapterListNameCity.setOnItemClickListener((view, position) -> {
            currentCityModel = iDataRecycler.getData(position);
            showCoatOfArms(currentCityModel);
        });
    }

    private void showCoatOfArms(CityModel cityModel) {
        Intent intent = new Intent(requireActivity(), SelectCityActivity.class);
        intent.putExtra("city", cityModel);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.delete_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.remove_context) {
            adapterListNameCity.removeCity(adapterListNameCity.getMenuPosition());
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.alert_add_city, null);
        MaterialButton btnAdd = view.findViewById(R.id.btn_add);
        MaterialButton btnCancel = view.findViewById(R.id.btn_cancel);
        TextInputEditText textCity = view.findViewById(R.id.textCity);
        builder.setView(view);
        AlertDialog alert = builder.create();
        btnAdd.setOnClickListener(v -> {
            if (!Objects.requireNonNull(textCity.getText()).toString().isEmpty()) {
                adapterListNameCity.addCity(textCity.getText().toString());
                alert.cancel();
            }
        });
        btnCancel.setOnClickListener(v -> {
            alert.cancel();
        });
        alert.show();
    }
}
