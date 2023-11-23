package com.example.weatherapp.Activitis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.Adapter.HourlyAdapter;
import com.example.weatherapp.Domains.Hourly;
import com.example.weatherapp.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = (View) inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView= inf.findViewById(R.id.recyclerView);

        initRecyclerView();

        return inf;

    }

    private void initRecyclerView() {
        ArrayList<Hourly> items = new ArrayList<>();
        items.add(new Hourly("10 pm", 28,"cloudy"));
        items.add(new Hourly("11 pm", 29,"sun"));
        items.add(new Hourly("12 pm", 30,"wind"));
        items.add(new Hourly("1 am", 29,"rainy"));
        items.add(new Hourly("2 am", 27,"storm"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false));

        adapterHourly = new HourlyAdapter(items);
        recyclerView.setAdapter(adapterHourly);

        Log.d("RecyclerView", "Item count: " + items.size());
        Log.d("RecyclerView", "Adapter: " + recyclerView.getAdapter());

    }
}