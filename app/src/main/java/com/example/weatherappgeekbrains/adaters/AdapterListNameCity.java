package com.example.weatherappgeekbrains.adaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.interfaces.IDataRecycler;
import com.example.weatherappgeekbrains.models.CityModel;
import com.google.android.material.textview.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListNameCity extends RecyclerView.Adapter<AdapterListNameCity.ViewHolder> {

    private IDataRecycler dataCities;
    private OnItemClickListener itemClickListener;

    public AdapterListNameCity(IDataRecycler dataCities) {
        this.dataCities = dataCities;
    }

    @NonNull
    @Override
    public AdapterListNameCity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_cities, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (itemClickListener != null) {
            holder.setOnClickListener(itemClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityModel cityModel = dataCities.getData(position);
        holder.nameCity.setText(cityModel.getNameCity());
    }

    @Override
    public int getItemCount() {
        return dataCities.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name_city)
        MaterialTextView nameCity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setOnClickListener(final OnItemClickListener listener) {
            nameCity.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return;
                listener.onItemClick(v, adapterPosition);
            });
        }
    }
}