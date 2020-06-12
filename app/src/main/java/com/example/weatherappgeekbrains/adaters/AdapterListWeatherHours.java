package com.example.weatherappgeekbrains.adaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.models.ModelGetWeatherFromCor.DataWeatherFromCor;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.UntilTimes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListWeatherHours extends RecyclerView.Adapter<AdapterListWeatherHours.ViewHolder> {

    private DataWeatherFromCor dataWeather;
    private Context context;

    public AdapterListWeatherHours(DataWeatherFromCor dataWeather, Context context) {
        this.dataWeather = dataWeather;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterListWeatherHours.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_days_or_hours_weather, parent, false);
        return new AdapterListWeatherHours.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListWeatherHours.ViewHolder holder, int position) {
        initViewForHourly(holder);
    }

    @Override
    public int getItemCount() {
        return dataWeather.getHourly().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTemperature)
        TextView txtTemperature;

        @BindView(R.id.imgWeather)
        ImageView imgWeather;

        @BindView(R.id.textHumidity)
        TextView textHumidity;

        @BindView(R.id.textDate)
        TextView textDate;

        ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void initViewForHourly(AdapterListWeatherHours.ViewHolder holder) {
        Glide.with(context)
                .load(Constants.GET_IMG + dataWeather.getHourly().get(holder.getAdapterPosition()).getWeather().get(0).getIcon() + ".png")
                .centerCrop()
                .into(holder.imgWeather);

        holder.txtTemperature.setText(new StringBuilder(
                Double.valueOf(dataWeather.getHourly().get(holder.getAdapterPosition()).getTemp().toString()).intValue()
                        + " " +
                        context.getResources().getString(R.string.temperature_values)));

        holder.textHumidity.setText(new StringBuilder(Double.valueOf(dataWeather.getHourly().get(holder.getAdapterPosition())
                .getHumidity().toString()).intValue()
                + " " +
                context.getResources().getString(R.string.humidity_values)));

        holder.textDate.setText(UntilTimes.getTimeFromMil(dataWeather.getHourly().get(holder.getAdapterPosition()).getDt().longValue()));
    }
}
