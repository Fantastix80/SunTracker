package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.Adapter.FavoriteAdapter;
import com.example.weatherapp.Adapter.FavoriteData;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private RecyclerView.Adapter adapterCity;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = (View) inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = inf.findViewById(R.id.recyclerView);
        initRecyclerView();

        return inf;
    }

    private void initRecyclerView() {
        ArrayList<FavoriteData> items = new ArrayList<>();
        items.add(new FavoriteData("Ville", "20", "25", "28","clear"));
        items.add(new FavoriteData("Ville", "20", "23","29","clear"));
        items.add(new FavoriteData("Ville", "20", "42", "30","clouds"));
        items.add(new FavoriteData("Ville","20", "12", "29","rain"));
        items.add(new FavoriteData("Ville", "20", "14", "27","snow"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        adapterCity = new FavoriteAdapter(items);
        recyclerView.setAdapter(adapterCity);

        adapterCity.notifyDataSetChanged();

        Log.d("RecyclerView", "Item count: " + items.size());
        Log.d("RecyclerView", "Adapter: " + recyclerView.getAdapter());

    }
}