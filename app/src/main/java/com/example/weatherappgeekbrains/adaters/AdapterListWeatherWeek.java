package com.example.weatherappgeekbrains.adaters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.WeatherDayWeekModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListWeatherWeek extends RecyclerView.Adapter<AdapterListWeatherWeek.ViewHolder> {

    private IDataRecycler dataWeather;
    private Resources resources;

    public AdapterListWeatherWeek(IDataRecycler dataWeather) {
        this.dataWeather = dataWeather;
        this.resources = dataWeather.getResources();
    }

    @NonNull
    @Override
    public AdapterListWeatherWeek.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_week_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListWeatherWeek.ViewHolder holder, int position) {
        WeatherDayWeekModel weatherDayWeekModel = dataWeather.getData(position);
        holder.dayWeek.setText(weatherDayWeekModel.getDayWeek());
        holder.imageWeather.setImageDrawable(resources.getDrawable(weatherDayWeekModel.getIdImage()));
        holder.statusWeather.setText(weatherDayWeekModel.getStatusWeather());
        holder.itemWeekTemperature.setText(weatherDayWeekModel.getTemperature());
    }

    @Override
    public int getItemCount() {
        return dataWeather.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageWeather)
        ImageView imageWeather;

        @BindView(R.id.dayWeek)
        TextView dayWeek;

        @BindView(R.id.statusWeather)
        TextView statusWeather;

        @BindView(R.id.itemWeekTemperature)
        TextView itemWeekTemperature;

        ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
