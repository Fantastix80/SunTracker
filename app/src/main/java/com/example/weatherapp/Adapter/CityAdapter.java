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
import com.example.weatherapp.Domains.CityData;
import com.example.weatherapp.R;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    ArrayList<CityData> items;
    Context context;

    public CityAdapter(ArrayList<CityData> items){
        this.items = items;
    }
    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_view, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        holder.cityTextView.setText(items.get(position).getCity());
        holder.minDegreeTextView.setText(items.get(position).getMinDegree()+"°");
        holder.maxDegreeTextView.setText(items.get(position).getMaxDegree()+"°");

        int drawableRessourceId=holder.itemView.getResources()
                .getIdentifier(items.get(position).getWeatherIcon(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableRessourceId)
                .into(holder.weatherIconImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityTextView, maxDegreeTextView, minDegreeTextView;
        ImageView weatherIconImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.cityText);
            maxDegreeTextView = itemView.findViewById(R.id.maxDegree);
            minDegreeTextView = itemView.findViewById(R.id.minDegree);
            weatherIconImageView = itemView.findViewById(R.id.wheaterIcon);
        }
    }
}
