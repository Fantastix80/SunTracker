package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.manager.APIManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

//import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private ActivityMainBinding binding;
    protected LocationManager locationManager;
    private APIManager apiManager;
    boolean askPlayerLocation = true;
    private FloatingActionButton fbSearchBtn;
    private Map<String, String> weatherData;
    private GifImageView gifLoader;
    private FrameLayout frameLayout;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler = new Handler();

        gifLoader = findViewById(R.id.gifLoader);
        gifLoader.setVisibility(View.VISIBLE);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.GONE);

        weatherData = new HashMap<String, String>();

        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment(weatherData));
                    break;

                case R.id.navigation_favorite:
                    replaceFragment(new FavoriteFragment());
                    break;
            }

            return true;
        });

        fbSearchBtn = findViewById(R.id.fbSearchBtn);
        fbSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(search);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Dans le cas où l'utilisateur viendrait à refuser de partager sa localisation, on vient faire un early return.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Afficher une popup demandant à l'utilisateur d'activer l'accès à sa localisation.
            // TODO: Cette popup devra empêcher l'utilisateur d'utiliser l'app tant que ce paramètre ne sera pas activé.
            Toast.makeText(getApplicationContext(), "Veuillez autoriser l'application d'accéder à votre localisation afin de pouvoir l'utiliser.", Toast.LENGTH_LONG).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 5000);

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        apiManager = new APIManager(getApplicationContext());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (askPlayerLocation) {
            //on définie notre listener qui va s'activer lorsque l'api aura répondu à notre requête
            APIManager.VolleyResponseListener weatherListener = new APIManager.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(JSONObject response) {
                    fillWeatherData(response);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replaceFragment(new HomeFragment(weatherData));
                            gifLoader.setVisibility(View.GONE);
                            frameLayout.setVisibility(View.VISIBLE);
                        }
                    }, 1000);

                }
            };

            apiManager.getWeatherFromLocation("forecast", location, weatherListener);
            askPlayerLocation = false;
        }
    }

    private void fillWeatherData(JSONObject response) {
        try {
            JSONObject cityInfos = response.getJSONObject("city");
            weatherData.put("city", cityInfos.getString("name"));
            weatherData.put("country", cityInfos.getString("country"));

            JSONArray list = response.getJSONArray("list");
            JSONObject currentMainData = list.getJSONObject(0);

            weatherData.put("date", currentMainData.getString("dt_txt"));

            JSONObject windInfos = currentMainData.getJSONObject("wind");
            weatherData.put("windSpeed", Integer.toString((int) Math.round(Float.parseFloat(windInfos.getString("speed")) * 3.6)));

            JSONObject cloudInfos = currentMainData.getJSONObject("clouds");
            weatherData.put("pourcentCloud", cloudInfos.getString("all"));

            JSONArray currentWeatherArray = currentMainData.getJSONArray("weather");
            JSONObject currentWeatherData = currentWeatherArray.getJSONObject(0);
            weatherData.put("mainWeather", currentWeatherData.getString("main"));
            weatherData.put("description", currentWeatherData.getString("description"));

            JSONObject currentMain = currentMainData.getJSONObject("main");
            weatherData.put("currentTemperature", currentMain.getString("temp"));
            weatherData.put("currentMaxTemperature", currentMain.getString("temp_max"));
            weatherData.put("currentMinTemperature", currentMain.getString("temp_min"));
            weatherData.put("pourcentHumidity", currentMain.getString("humidity"));

            // On met les data de la météo en rapport avec les prévisions horaires
            for (int i = 0; i <= 39; i++) {
                JSONObject jsonObj = list.getJSONObject(i);
                weatherData.put("main_" + i, jsonObj.getJSONArray("weather").getJSONObject(0).getString("main"));
                weatherData.put("temperature_" + i, jsonObj.getJSONObject("main").getString("temp"));
                weatherData.put("maxTemperature_" + i, jsonObj.getJSONObject("main").getString("temp_max"));
                weatherData.put("minTemperature_" + i, jsonObj.getJSONObject("main").getString("temp_min"));
                weatherData.put("time_" + i, jsonObj.getString("dt_txt"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}