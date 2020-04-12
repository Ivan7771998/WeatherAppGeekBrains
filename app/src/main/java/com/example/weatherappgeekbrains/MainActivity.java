package com.example.weatherappgeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

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
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showState("onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter= MainPresenter.getInstance();
        initWorkViewCheckBox();
    }

    private void initWorkViewCheckBox() {
        showPrecipitation.setVisibility(presenter.getShowPrecipitation());
        showHumidity.setVisibility(presenter.getShowHumidity());
        showWind.setVisibility(presenter.getShowWind());
        checkShowPrecipitation(showPrecipitation.isChecked());
        checkShowHumidity(showHumidity.isChecked());
        checkShowWind(showWind.isChecked());

        showPrecipitation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkShowPrecipitation(isChecked);
        });

        showHumidity.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkShowHumidity(isChecked);
        });

        showWind.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkShowWind(isChecked);
        });
    }

    private void checkShowPrecipitation(boolean isChecked){
        if (isChecked) {
            textHumidity.setVisibility(View.VISIBLE);
        } else {
            textHumidity.setVisibility(View.GONE);
        }
    }

    private void checkShowHumidity(boolean isChecked){
        if (isChecked) {
            textPrecipitation.setVisibility(View.VISIBLE);
        } else {
            textPrecipitation.setVisibility(View.GONE);
        }
    }

    private void checkShowWind(boolean isChecked){
        if (isChecked) {
            textWind.setVisibility(View.VISIBLE);
        } else {
            textWind.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        showState("onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        showState("onResume()");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        showState("onRestart()");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        showState("onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        showState("onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        showState("onDestroy()");
        super.onDestroy();
    }

    private void showState(String nameState) {
        Log.d(TAG, nameState);
        Toast.makeText(getApplicationContext(), new StringBuilder()
                .append(nameState).append(" method called!"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        showState("onSaveInstanceState()");
    }
}
