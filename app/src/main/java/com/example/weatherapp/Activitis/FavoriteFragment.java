package com.example.weatherapp.Activitis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.Adapter.CityAdapter;
import com.example.weatherapp.Domains.CityData;
import com.example.weatherapp.R;

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
        ArrayList<CityData> items = new ArrayList<>();
        items.add(new CityData("10 pm",25, 28,"cloudy"));
        items.add(new CityData("11 pm", 23,29,"sun"));
        items.add(new CityData("12 pm", 42, 30,"wind"));
        items.add(new CityData("1 am",12, 29,"rainy"));
        items.add(new CityData("2 am", 14, 27,"storm"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        adapterCity = new CityAdapter(items);
        recyclerView.setAdapter(adapterCity);

        adapterCity.notifyDataSetChanged();

        Log.d("RecyclerView", "Item count: " + items.size());
        Log.d("RecyclerView", "Adapter: " + recyclerView.getAdapter());

    }
}