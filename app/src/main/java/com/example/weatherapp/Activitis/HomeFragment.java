package com.example.weatherapp.Activitis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.Adapter.HourlyAdapter;
import com.example.weatherapp.Adapter.TommorowAdapter;
import com.example.weatherapp.Domains.Hourly;
import com.example.weatherapp.Domains.TommorowDomain;
import com.example.weatherapp.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView.Adapter adapterTommorow;
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = (View) inflater.inflate(R.layout.fragment_home, container, false);

        TextView nextDayTextView = inf.findViewById(R.id.nextDay);
        TextView todayTxtView = inf.findViewById(R.id.todayTxt);

        nextDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecyclerDViewData();
            }
        });

        todayTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerView();
            }
        });

        recyclerView = inf.findViewById(R.id.recyclerView);
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

        adapterHourly.notifyDataSetChanged();

        Log.d("RecyclerView", "Item count: " + items.size());
        Log.d("RecyclerView", "Adapter: " + recyclerView.getAdapter());

    }

    private void updateRecyclerDViewData() {
        ArrayList<TommorowDomain> items = new ArrayList<>();

        items.add(new TommorowDomain("Sat", "storm", 25, 10));
        items.add(new TommorowDomain("Sun", "cloudy",  24, 16));
        items.add(new TommorowDomain("Mon", "cloudy_3", 29, 15));
        items.add(new TommorowDomain("Tue", "cloudy_sunny",  22, 13));
        items.add(new TommorowDomain("Wen", "sun", 19,11));
        items.add(new TommorowDomain("Thu", "rainy", 17,12));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false));

        adapterTommorow = new TommorowAdapter(items);
        recyclerView.setAdapter(adapterTommorow);
        adapterTommorow.notifyDataSetChanged();
    }

}