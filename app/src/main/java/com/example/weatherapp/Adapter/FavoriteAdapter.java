package com.example.weatherapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    ArrayList<FavoriteData> items;
    Context context;

    public FavoriteAdapter(ArrayList<FavoriteData> items){
        this.items = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_favorite, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCity.setText(items.get(position).getCity());
        holder.tvTemperature.setText(items.get(position).getTemperature()+" °C");
        holder.tvMaxTemperature.setText(items.get(position).getMaxDegree()+" °C");
        holder.tvMinTemperature.setText(items.get(position).getMinDegree()+" °C");

        int drawableRessourceId=holder.itemView.getResources()
                .getIdentifier(items.get(position).getWeatherIcon(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableRessourceId)
                .into(holder.ivWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCity, tvTemperature, tvMaxTemperature, tvMinTemperature;
        private ImageView ivWeatherIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvMaxTemperature = itemView.findViewById(R.id.tvMaxTemperature);
            tvMinTemperature = itemView.findViewById(R.id.tvMinTemperature);
            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
        }
    }
}
