package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.Adapter.Hourly;
import com.example.weatherapp.Adapter.HourlyAdapter;
import com.example.weatherapp.Adapter.TomorrowAdapter;
import com.example.weatherapp.Adapter.TomorrowDomain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class HomeFragment extends Fragment {

    private RecyclerView.Adapter adapterHourly;
    private RecyclerView.Adapter adapterTommorow;
    private RecyclerView recyclerView;
    private TextView tvCity, tvDate, tvTemperature, tvDescription, tvFormatedHighestLowest, tvPourcentCloud, tvWindSpeed, tvPourcentHumidity, nextDayTextView, todayTxtView;
    private String city, date, currentTemperature, mainWeather, description, formatedHighestLowest, pourcentCloud, windSpeed, pourcentHumidity;
    private Map<String, String> weatherData;

    public HomeFragment(Map<String, String> weatherData) {
        this.weatherData = weatherData;

        this.city = weatherData.get("city") + ", " + weatherData.get("country");
        this.currentTemperature = (int) Math.round(Float.parseFloat(weatherData.get("currentTemperature"))) + " °C";
        this.mainWeather = weatherData.get("mainWeather");
        this.description = weatherData.get("description");
        int tmpMaxTemp = (int) Math.round(Float.parseFloat(weatherData.get("currentMaxTemperature")));
        int tmpMinTemp = (int) Math.round(Float.parseFloat(weatherData.get("currentMinTemperature")));
        this.formatedHighestLowest = "Max: " + tmpMaxTemp + " °C  |  Min: " + tmpMinTemp + " °C";
        this.windSpeed = (int) Math.round(Float.parseFloat(weatherData.get("windSpeed")) * 3.6) + " km/h";
        this.pourcentHumidity = weatherData.get("pourcentHumidity") + " %";
        this.pourcentCloud = weatherData.get("pourcentCloud") + " %";


        this.date = getFormatedDate(weatherData.get("date"));
    }

    private String getFormatedDate(String date) {
        String result = "";

        try {
            DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = (Date)inputFormatter.parse(date);

            DateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy");
            result = outputFormatter.format(inputDate);
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    private String getFormatedDate2(String date) {
        String result = "";

        try {
            DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = (Date)inputFormatter.parse(date);

            DateFormat outputFormatter = new SimpleDateFormat("dd MMM");
            result = outputFormatter.format(inputDate);
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    private String getFormatedTime(String datetime) {
        String result = "";

        String time = datetime.split(" ")[1];
        int hour = Integer.parseInt(time.split(":")[0]) - 12;
        if (hour < 0) {
            result = (hour + 12) + " am";
        } else if (hour > 0) {
            result = hour + " pm";
        } else {
            result = "12 am";
        }

        return result;
    }

    private void initRecyclerView() {
        ArrayList<Hourly> items = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            System.out.println(weatherData.get("main_" + i));
            items.add(new Hourly(getFormatedTime(weatherData.get("time_" + i)), Math.round(Float.parseFloat(weatherData.get("temperature_" + i))) + " °C", weatherData.get("main_" + i).toLowerCase()));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false));

        adapterHourly = new HourlyAdapter(items);
        recyclerView.setAdapter(adapterHourly);
    }

    private void updateRecyclerDViewData() {
        ArrayList<TomorrowDomain> items = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            if (i != 0 && i % 8 == 0) {
                System.out.println(weatherData.get("main_" + i));
                items.add(new TomorrowDomain(getFormatedDate2(weatherData.get("time_" + i)), weatherData.get("main_" + i).toLowerCase(), Math.round(Float.parseFloat(weatherData.get("maxTemperature_" + i))) + " °C", Math.round(Float.parseFloat(weatherData.get("minTemperature_" + i))) + " °C"));
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false));

        adapterTommorow = new TomorrowAdapter(items);
        recyclerView.setAdapter(adapterTommorow);
        adapterTommorow.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = (View) inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = inf.findViewById(R.id.recyclerView);

        initRecyclerView();

        nextDayTextView = (TextView) inf.findViewById(R.id.nextDay);
        todayTxtView = (TextView) inf.findViewById(R.id.todayTxt);

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

        tvCity = (TextView) inf.findViewById(R.id.tvCity);
        tvCity.setText(city);
        tvDate = (TextView) inf.findViewById(R.id.tvDate);
        tvDate.setText(date);
        tvTemperature = (TextView) inf.findViewById(R.id.tvTemperature);
        tvTemperature.setText(currentTemperature);
        tvDescription = (TextView) inf.findViewById(R.id.tvDescription);
        tvDescription.setText(description);
        tvFormatedHighestLowest = (TextView) inf.findViewById(R.id.tvFormatedHighestLowest);
        tvFormatedHighestLowest.setText(formatedHighestLowest);
        tvPourcentCloud = (TextView) inf.findViewById(R.id.tvPourcentCloud);
        tvPourcentCloud.setText(pourcentCloud);
        tvWindSpeed = (TextView) inf.findViewById(R.id.tvWindSpeed);
        tvWindSpeed.setText(windSpeed);
        tvPourcentHumidity = (TextView) inf.findViewById(R.id.tvPourcentHumidity);
        tvPourcentHumidity.setText(pourcentHumidity);

        return inf;
    }
}