package com.example.weatherappgeekbrains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCityActivity extends AppCompatActivity {

    @BindView(R.id.materialButton)
    MaterialButton materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        materialButton.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}
