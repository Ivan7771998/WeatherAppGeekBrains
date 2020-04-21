package com.example.weatherappgeekbrains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCityActivity extends AppCompatActivity {

    @BindView(R.id.materialButton)
    MaterialButton materialButton;
    @BindView(R.id.searchCityText)
    TextInputEditText searchCityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);

        materialButton.setOnClickListener(v -> {
            if (!Objects.requireNonNull(searchCityText.getText()).toString().equals("")) {
                startActivity(MainActivity.newInstance(this, searchCityText.getText().toString()));
            } else {
                Toast.makeText(this, getResources().getString(R.string.text_toast), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
