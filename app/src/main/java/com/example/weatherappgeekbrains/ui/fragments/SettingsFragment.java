package com.example.weatherappgeekbrains.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.ui.activities.MainActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    @BindView(R.id.setSwitchTheme)
    SwitchMaterial switchMaterial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        switchMaterial.setChecked(mainActivity.isDarkTheme());
        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mainActivity.setDarkTheme(true);
            } else {
                mainActivity.setDarkTheme(false);
            }
            mainActivity.recreate();
        });

    }
}
