package com.example.weatherappgeekbrains.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherappgeekbrains.App;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListNameCity;
import com.example.weatherappgeekbrains.data.DataCitiesBuilder;
import com.example.weatherappgeekbrains.database.CityDao;
import com.example.weatherappgeekbrains.database.entities.EntityCity;
import com.example.weatherappgeekbrains.database.entities.EntityCityAndWeatherDesc;
import com.example.weatherappgeekbrains.database.entities.EntityMyLocation;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.tools.MySharedPref;
import com.example.weatherappgeekbrains.ui.dialogs.DialogAddNewCity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.weatherappgeekbrains.tools.Constants.PERMISSION_REQUEST_CODE;
import static com.example.weatherappgeekbrains.ui.fragments.CoatOfArmsFragment.CITY_DATA_FR;


public class CitiesFragment extends Fragment {

    private EntityCity currentCityModel;
    private IDataRecycler iDataRecycler;
    private CityDao cityDao;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        if (!MySharedPref.getCurrentCity().equals("") && MySharedPref.getNetWorkConnect()) {
//            try {
//                showCoatOfArms(App.getInstance().getCityDao().getCityByName(MySharedPref.getCurrentCity()).id);
//            } catch (Exception e) {
//                MySharedPref.setCurrentCity("");
//            }
//        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        requestPermissions();
        iDataRecycler = initDataCities();
        initListCities(iDataRecycler);
        initFAB();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentCityModel = savedInstanceState.getParcelable("city");
        }
    }

    private void initCityDao() {
        cityDao = App.getInstance().getCityDao();
    }

    private IDataRecycler initDataCities() {
        initCityDao();
        return new DataCitiesBuilder().setResources(cityDao).build();
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
            if (MySharedPref.getNetWorkConnect()) {
                currentCityModel = iDataRecycler.getData(position);
                showCoatOfArms(currentCityModel.id);
            } else {
                if (getActivity() != null)
                    Toast.makeText(getContext(), getActivity().getString(R.string.not_internet_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCoatOfArms(long idCity) {
        Bundle args = new Bundle();
        args.putLong(CITY_DATA_FR, idCity);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_nav_weather_to_nav_selected_city_weather, args);
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
        DialogAddNewCity dialogAddNewCity = DialogAddNewCity.newInstance(adapterListNameCity);
        dialogAddNewCity.show(requireActivity().getSupportFragmentManager(), "DialogAddNewCity");
    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            requestLocationPermissions();
        }
    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            }
        }
    }

    // Запрашиваем координаты
    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 20, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude(); // Широта
                    double lng = location.getLongitude(); // Долгота
                    EntityMyLocation entityMyLocation = new EntityMyLocation();
                    entityMyLocation.latitude = lat;
                    entityMyLocation.longitude = lng;
                    entityMyLocation.timestamp = System.currentTimeMillis();
                    App.getInstance().getCityDao().insertMyLocation(entityMyLocation);


                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }
    }
}
