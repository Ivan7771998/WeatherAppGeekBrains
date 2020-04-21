package com.example.weatherappgeekbrains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private final static String NAME_CITY = "name_city";

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
    @BindView(R.id.titleWeather)
    TextView titleWeather;
    @BindView(R.id.showInBrowserBtn)
    MaterialButton showInBrowserBtn;

    private MainPresenter presenter;

    public static Intent newInstance(Context context, String nameCity) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(NAME_CITY, nameCity);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = MainPresenter.getInstance();
        setTitleWeather();
        initWorkViewCheckBox();
        openWeatherInBrowser();
    }

    private void openWeatherInBrowser() {
        showInBrowserBtn.setOnClickListener(v -> {
            startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://yandex.ru/pogoda/moscow")));
        });
    }

    private void setTitleWeather() {
        if (Objects.requireNonNull(getIntent().getExtras()).containsKey(NAME_CITY)) {
            presenter.setTitleWeather(getIntent().getStringExtra(NAME_CITY));
            titleWeather.setText(presenter.getTitleWeather());
        }
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
            if (!"".contentEquals(severalText.getText())) {
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
}
