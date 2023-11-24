package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Adapter.Hourly;
import com.example.weatherapp.Adapter.HourlyAdapter;
import com.example.weatherapp.Adapter.TomorrowAdapter;
import com.example.weatherapp.Adapter.TomorrowDomain;
import com.example.weatherapp.manager.APIManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class CityWeatherActivity extends AppCompatActivity {

    private ImageView BackToSearchBtn, addToFavoriteBtn;
    private APIManager apiManager;
    private String city, country, date, currentTemperature, mainWeather, description, formatedHighestLowest, pourcentCloud, windSpeed, pourcentHumidity;
    private boolean isInFavorite = false;
    private TextView tvCity, tvDate, tvTemperature, tvDescription, tvFormatedHighestLowest, tvPourcentCloud, tvWindSpeed, tvPourcentHumidity, nextDayTextView, todayTxtView;
    private Map<String, String> cityWeatherData;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView.Adapter adapterTommorow;
    private LinearLayout linearLayoutStar, linearLayoutArrowBack;
    private ScrollView scrollView;
    private GifImageView gifLoader;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        linearLayoutStar = findViewById(R.id.linearLayoutStar);
        linearLayoutArrowBack = findViewById(R.id.linearLayoutArrowBack);
        scrollView = findViewById(R.id.scrollView);
        gifLoader = findViewById(R.id.gifLoader);

        linearLayoutStar.setVisibility(View.GONE);
        linearLayoutArrowBack.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        gifLoader.setVisibility(View.VISIBLE);

        handler = new Handler();

        cityWeatherData = new HashMap<String, String>();

        apiManager = new APIManager(getApplicationContext());

        BackToSearchBtn = findViewById(R.id.BackToSearchBtn);
        BackToSearchBtn.bringToFront();
        BackToSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(search);
                finish();
            }
        });

        addToFavoriteBtn = findViewById(R.id.addToFavoriteBtn);
        addToFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                APIManager.VolleyResponseListener favoritelistener = new APIManager.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("success").equals("false")) {
                                Toast.makeText(getApplicationContext(), response.getString("error"), Toast.LENGTH_LONG).show();
                                if (isInFavorite) {
                                    isInFavorite = false;
                                    addToFavoriteBtn.setImageResource(R.drawable.baseline_star_border_24);
                                } else {
                                    isInFavorite = true;
                                    addToFavoriteBtn.setImageResource(R.drawable.baseline_star_24);
                                }
                            }
                        } catch (JSONException e) {
                            if (isInFavorite) {
                                isInFavorite = false;
                                addToFavoriteBtn.setImageResource(R.drawable.baseline_star_border_24);
                            } else {
                                isInFavorite = true;
                                addToFavoriteBtn.setImageResource(R.drawable.baseline_star_24);
                            }
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                };

                // Actualiser la ville dans la bdd (l'ajouter ou la retirer)
                if (isInFavorite) {
                    isInFavorite = false;
                    addToFavoriteBtn.setImageResource(R.drawable.baseline_star_border_24);
                    apiManager.updateFavorite("delete", city.split(",")[0], favoritelistener);
                } else {
                    isInFavorite = true;
                    addToFavoriteBtn.setImageResource(R.drawable.baseline_star_24);
                    apiManager.updateFavorite("add", city.split(",")[0], favoritelistener);
                }
            }
        });

        APIManager.VolleyResponseListener weatherListener = new APIManager.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject response) {
                fillWeatherData(response);

                initRecyclerView();

                tvCity = (TextView) findViewById(R.id.tvCity);
                tvCity.setText(city);
                tvDate = (TextView) findViewById(R.id.tvDate);
                tvDate.setText(date);
                tvTemperature = (TextView) findViewById(R.id.tvTemperature);
                tvTemperature.setText(currentTemperature);
                tvDescription = (TextView) findViewById(R.id.tvDescription);
                tvDescription.setText(description);
                tvFormatedHighestLowest = (TextView) findViewById(R.id.tvFormatedHighestLowest);
                tvFormatedHighestLowest.setText(formatedHighestLowest);
                tvPourcentCloud = (TextView) findViewById(R.id.tvPourcentCloud);
                tvPourcentCloud.setText(pourcentCloud);
                tvWindSpeed = (TextView) findViewById(R.id.tvWindSpeed);
                tvWindSpeed.setText(windSpeed);
                tvPourcentHumidity = (TextView) findViewById(R.id.tvPourcentHumidity);
                tvPourcentHumidity.setText(pourcentHumidity);

                APIManager.VolleyResponseListener isInFavoriteListener = new APIManager.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("exist").equals("true")) {
                                isInFavorite = true;
                                addToFavoriteBtn.setImageResource(R.drawable.baseline_star_24);
                            }

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    linearLayoutStar.setVisibility(View.VISIBLE);
                                    linearLayoutArrowBack.setVisibility(View.VISIBLE);
                                    scrollView.setVisibility(View.VISIBLE);
                                    gifLoader.setVisibility(View.GONE);
                                }
                            }, 1000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                };

                apiManager.isInFavorite(city.split(",")[0], isInFavoriteListener);
            }
        };

        nextDayTextView = (TextView) findViewById(R.id.nextDay);
        todayTxtView = (TextView) findViewById(R.id.todayTxt);

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

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            city = extras.getString("city");
            apiManager.getWeatherFromCity("forecast", city, weatherListener);
        } else {
            System.out.println("Extras vide dans l'activité CityWeatherActivity, retour à l'activité précédente");
            Intent erreurExtras = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(erreurExtras);
            finish();
        }
    }

    private void fillWeatherData(JSONObject response) {
        try {
            if (response.getString("cod").equals("200")) {
                JSONObject cityInfos = response.getJSONObject("city");
                country = cityInfos.getString("country");

                city = city + ", " + country;

                JSONArray list = response.getJSONArray("list");
                JSONObject currentMainData = list.getJSONObject(0);

                date = getFormatedDate(currentMainData.getString("dt_txt"));

                JSONObject windInfos = currentMainData.getJSONObject("wind");
                windSpeed = (int) Math.round(Float.parseFloat(windInfos.getString("speed")) * 3.6) + " km/h";

                JSONObject cloudInfos = currentMainData.getJSONObject("clouds");
                pourcentCloud = cloudInfos.getString("all") + " %";

                JSONArray currentWeatherArray = currentMainData.getJSONArray("weather");
                JSONObject currentWeatherData = currentWeatherArray.getJSONObject(0);
                mainWeather = currentWeatherData.getString("main");
                description = currentWeatherData.getString("description");

                JSONObject currentMain = currentMainData.getJSONObject("main");
                currentTemperature = (int) Math.round(Float.parseFloat(currentMain.getString("temp"))) + " °C";
                int currentMaxTemperature = (int) Math.round(Float.parseFloat(currentMain.getString("temp_max")));
                int currentMinTemperature = (int) Math.round(Float.parseFloat(currentMain.getString("temp_min")));
                formatedHighestLowest = "Max: " + currentMaxTemperature + " °C  |  Min: " + currentMinTemperature + " °C";
                pourcentHumidity = currentMain.getString("humidity") + " %";

                // On met les data de la météo en rapport avec les prévisions horaires
                for (int i = 0; i <= 39; i++) {
                    JSONObject jsonObj = list.getJSONObject(i);
                    cityWeatherData.put("main_" + i, jsonObj.getJSONArray("weather").getJSONObject(0).getString("main"));
                    System.out.println(jsonObj.getJSONArray("weather").getJSONObject(0).getString("main"));
                    cityWeatherData.put("temperature_" + i, jsonObj.getJSONObject("main").getString("temp"));
                    cityWeatherData.put("maxTemperature_" + i, jsonObj.getJSONObject("main").getString("temp_max"));
                    cityWeatherData.put("minTemperature_" + i, jsonObj.getJSONObject("main").getString("temp_min"));
                    cityWeatherData.put("time_" + i, jsonObj.getString("dt_txt"));
                }
            } else {
                Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                searchActivity.putExtra("errors", "La ville demandé n'a pas été trouvé !");
                startActivity(searchActivity);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
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

    private void initRecyclerView() {
        ArrayList<Hourly> items = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            items.add(new Hourly(getFormatedTime(cityWeatherData.get("time_" + i)), Math.round(Float.parseFloat(cityWeatherData.get("temperature_" + i))) + " °C", cityWeatherData.get("main_" + i).toLowerCase()));
        }

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false));

        adapterHourly = new HourlyAdapter(items);
        recyclerView.setAdapter(adapterHourly);
    }

    private void updateRecyclerDViewData() {
        ArrayList<TomorrowDomain> items = new ArrayList<>();
        for (int i = 0; i <= 39; i++) {
            if (i != 0 && i % 8 == 0) {
                System.out.println(cityWeatherData.get("main_" + i));
                items.add(new TomorrowDomain(getFormatedDate2(cityWeatherData.get("time_" + i)), cityWeatherData.get("main_" + i).toLowerCase(), Math.round(Float.parseFloat(cityWeatherData.get("maxTemperature_" + i))) + " °C", Math.round(Float.parseFloat(cityWeatherData.get("minTemperature_" + i))) + " °C"));
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false));

        adapterTommorow = new TomorrowAdapter(items);
        recyclerView.setAdapter(adapterTommorow);
        adapterTommorow.notifyDataSetChanged();
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
}