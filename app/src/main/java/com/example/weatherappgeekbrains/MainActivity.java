package com.example.weatherappgeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

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
    @BindView(R.id.severalText)
    EditText severalText;
    @BindView(R.id.textViewCheck)
    TextView textViewCheck;
    @BindView(R.id.setButtonText)
    MaterialButton setButtonText;


    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showState("onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = MainPresenter.getInstance();
        initWorkViewCheckBox();
    }

    private void initWorkViewCheckBox() {
        textViewCheck.setVisibility(presenter.getShowCheckView());
        textViewCheck.setText(presenter.getTextCheckView());
        showPrecipitation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkVisibility(textPrecipitation, isChecked);
        });

        showHumidity.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkVisibility(textHumidity, isChecked);
        });

        showWind.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkVisibility(textWind, isChecked);
        });

        setButtonText.setOnClickListener(v -> {
            if(!"".contentEquals(severalText.getText())){
                presenter.setShowCheckView(View.VISIBLE);
                presenter.setTextCheckView(severalText.getText().toString());
                textViewCheck.setVisibility(presenter.getShowCheckView());
                textViewCheck.setText(presenter.getTextCheckView());
            }
        });
    }

    private void checkVisibility(TextView text, boolean isChecked) {
        if (isChecked) {
            text.setVisibility(View.VISIBLE);
        } else {
            text.setVisibility(View.GONE);
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
