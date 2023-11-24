package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.weatherapp.Adapter.FavoriteAdapter;
import com.example.weatherapp.Adapter.FavoriteData;
import com.example.weatherapp.manager.APIManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class FavoriteFragment extends Fragment {

    private RecyclerView.Adapter adapterCity;
    private RecyclerView recyclerView;

    private String city, country, date, currentTemperature, mainWeather, description, formatedHighestLowest, pourcentCloud, windSpeed, pourcentHumidity;
    private APIManager apiManager;
    private ArrayList<FavoriteData> items;
    private int compteurAPI = 0;
    private GifImageView gifLoader;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = (View) inflater.inflate(R.layout.fragment_favorite, container, false);

        gifLoader = inf.findViewById(R.id.gifLoader);
        scrollView = inf.findViewById(R.id.scrollView);
        gifLoader.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        recyclerView = inf.findViewById(R.id.recyclerView);
        apiManager = new APIManager(getActivity().getApplicationContext());

        items = new ArrayList<>();

        APIManager.VolleyResponseListener cityListener = new APIManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                fillWeatherData(response);
            }
        };

        apiManager.getAllFavorite(cityListener);

        initRecyclerView();

        return inf;
    }

    private void fillWeatherData(JSONObject response) {
        try {
            if (response.getString("empty").equals("false")) {
                JSONArray cityList = response.getJSONArray("data");
                APIManager.VolleyResponseListener listener = new APIManager.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("cod").equals("200")) {
                                JSONArray list = response.getJSONArray("list");
                                JSONObject weatherData = list.getJSONObject(0);
                                JSONObject mainData = weatherData.getJSONObject("main");

                                String city = response.getJSONObject("city").getString("name") + ", " + response.getJSONObject("city").getString("country");
                                String temperature = Math.round(Float.parseFloat(mainData.getString("temp"))) + " °C";
                                String maxTemperature = Math.round(Float.parseFloat(mainData.getString("temp_max"))) + " °C";
                                String minTemperature = Math.round(Float.parseFloat(mainData.getString("temp_min"))) + " °C";
                                String main = weatherData.getJSONArray("weather").getJSONObject(0).getString("main");

                                items.add(new FavoriteData(city, temperature, maxTemperature, minTemperature, main.toLowerCase()));

                                compteurAPI++;
                                if (compteurAPI == cityList.length()) {
                                    initRecyclerView();
                                    gifLoader.setVisibility(View.GONE);
                                    scrollView.setVisibility(View.VISIBLE);
                                }
                            } else {
                                System.out.println("Erreur lors de la réponse de l'api !");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                };
                for (int i = 0; i < cityList.length(); i++){
                    JSONObject tempVille = cityList.getJSONObject(i);
                    apiManager.getWeatherFromCity("forecast", tempVille.getString("ville"), listener);
                }

            } else {
                System.out.println("Aucune ville favorite");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        adapterCity = new FavoriteAdapter(items);
        recyclerView.setAdapter(adapterCity);

        adapterCity.notifyDataSetChanged();
    }
}