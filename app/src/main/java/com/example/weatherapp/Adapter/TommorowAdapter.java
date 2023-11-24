package com.example.weatherapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.Domains.TommorowDomain;
import com.example.weatherapp.R;

import java.util.ArrayList;

public class TommorowAdapter extends RecyclerView.Adapter<TommorowAdapter.ViewHolder> {

    ArrayList<TommorowDomain> items;
    Context context;

    public TommorowAdapter(ArrayList<TommorowDomain> items){
        this.items = items;
    }
    @NonNull
    @Override
    public TommorowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tommorow, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TommorowAdapter.ViewHolder holder, int position) {
        System.out.println(holder.dayTxt.getText());
        System.out.println(holder.highTempTxt.getText());
        System.out.println(holder.lowTempTxt.getText());

        System.out.println(items.get(position).getDay());
        System.out.println(items.get(position).getHighTemp());
        System.out.println(items.get(position).getLowTemp());
        System.out.println(items.get(position).getPicPath());
        //System.out.println(holder.pic.getImageMatrix());

        holder.dayTxt.setText(items.get(position).getDay());
        holder.highTempTxt.setText(items.get(position).getHighTemp()+"°");
        holder.lowTempTxt.setText(items.get(position).getLowTemp()+"°");


        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(items.get(position).getPicPath(), "drawable", holder.itemView.getContext().getPackageName());

        System.out.println("drawable crash");

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dayTxt, lowTempTxt, highTempTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            dayTxt = itemView.findViewById(R.id.dayTxt);
            lowTempTxt = itemView.findViewById(R.id.lowTempTxt);
            highTempTxt = itemView.findViewById(R.id.highTempTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
