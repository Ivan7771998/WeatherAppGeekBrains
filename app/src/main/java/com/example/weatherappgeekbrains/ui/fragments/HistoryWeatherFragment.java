package com.example.weatherappgeekbrains.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherappgeekbrains.App;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterHistoryWeatherCity;
import com.example.weatherappgeekbrains.data.DataHistoryBuilder;
import com.example.weatherappgeekbrains.database.entities.EntityCityAndWeatherDesc;
import com.example.weatherappgeekbrains.database.entities.EntityWeatherDesc;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment.CITY_DATA_FR;
import static com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment.CITY_DATA_HISTORY;

public class HistoryWeatherFragment extends Fragment {

    @BindView(R.id.idListHistory)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private AdapterHistoryWeatherCity adapterHistoryWeatherCity;

    public HistoryWeatherFragment() {
    }

    private IDataRecycler initDataHisWeather() {
        return new DataHistoryBuilder()
                .setResources(App.getInstance().getCityDao())
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initListHistoryWeather(initDataHisWeather());
    }

    private void initListHistoryWeather(IDataRecycler iDataRecycler) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        adapterHistoryWeatherCity
                = new AdapterHistoryWeatherCity(iDataRecycler, this);
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(requireContext(),
                        LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapterHistoryWeatherCity);
        adapterHistoryWeatherCity.setOnItemClickListener((view, position) -> {
            EntityCityAndWeatherDesc entityCityAndWeatherDesc = iDataRecycler.getData(position);
            showCoatOfArms(entityCityAndWeatherDesc.entityCity.id);
        });
    }

    private void showCoatOfArms(long idCity) {
        Bundle args = new Bundle();
        args.putLong(CITY_DATA_FR, idCity);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_history_weather_to_nav_selected_city_weather, args);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            adapterHistoryWeatherCity.removeItemHistory(adapterHistoryWeatherCity.getMenuPosition());
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
