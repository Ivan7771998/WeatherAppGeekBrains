package com.example.weatherappgeekbrains.adaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.database.entities.EntityWeatherDesc;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.tools.Constants;
import com.example.weatherappgeekbrains.tools.Tools;
import com.example.weatherappgeekbrains.ui.fragments.HistoryWeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterHistoryWeatherCity extends RecyclerView.Adapter<AdapterHistoryWeatherCity.ViewHolder> {

    private IDataRecycler dataHistory;
    private OnItemClickListener itemClickListener;
    private HistoryWeatherFragment fragment;
    private int menuPosition;

    public AdapterHistoryWeatherCity(IDataRecycler dataCities, HistoryWeatherFragment fragment) {
        this.dataHistory = dataCities;
        this.fragment = fragment;
    }

    @Override
    public AdapterHistoryWeatherCity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_history_weather, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (itemClickListener != null) {
            holder.setOnClickListener(itemClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EntityWeatherDesc entityWeatherDesc = dataHistory.getData(position);
        mapValues(holder, entityWeatherDesc);
        holder.itemView.setOnLongClickListener(v -> {
            menuPosition = position;
            return false;
        });

        if (fragment != null) {
            fragment.registerForContextMenu(holder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        return dataHistory.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void removeItemHistory(int position) {
        dataHistory.removeCity(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDateSearch)
        TextView txtDateSearch;

        @BindView(R.id.txtNameCity)
        TextView txtNameCity;

        @BindView(R.id.txtDescWeather)
        TextView txtDescWeather;

        @BindView(R.id.txtHistTemperature)
        TextView txtHistTemperature;

        @BindView(R.id.imgStatus)
        ImageView imgStatus;


        private View item;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            item = itemView;
        }

        void setOnClickListener(final OnItemClickListener listener) {
            item.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return;
                listener.onItemClick(v, adapterPosition);
            });
        }
    }

    private void mapValues(ViewHolder holder, EntityWeatherDesc entityWeatherDesc) {
        holder.txtDateSearch.setText(entityWeatherDesc.txtDate);
        holder.txtDescWeather.setText(entityWeatherDesc.txtDescription);
        holder.txtHistTemperature.setText(entityWeatherDesc.txtTemperature);
        holder.txtNameCity.setText(entityWeatherDesc.txtNameCity);
        Glide.with(fragment)
                .load(Constants.GET_IMG + entityWeatherDesc.txtImg + ".png")
                .centerCrop()
                .into(holder.imgStatus);
    }

    public int getMenuPosition() {
        return menuPosition;
    }
}
