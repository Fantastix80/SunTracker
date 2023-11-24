package com.example.weatherapp.manager;

import android.content.Context;
import android.location.Location;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIManager {
    private Context context;
    public RequestQueue queue;
    private String open_weather_api_key = "9ef338308ae6bffba5172fa321aa8d27";

    public APIManager(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    //Cette méthode permet de récupérer les réponses de l'api
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(JSONObject response);
    }

    public void getWeatherFromLocation(String type, Location location, final VolleyResponseListener listener) {

        String url = "";
        if (type == "weather" || type == "forecast") {
            url = "https://api.openweathermap.org/data/2.5/" + type + "?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&lang=fr&appid=" + open_weather_api_key;
        }
        System.out.println(url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getWeatherFromCity(String type, String city, final VolleyResponseListener listener) {

        String url = "";
        if (type == "weather" || type == "forecast") {
            url = "https://api.openweathermap.org/data/2.5/" + type + "?q=" + city + "&units=metric&lang=fr&appid=" + open_weather_api_key;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void isInFavorite(String city, final VolleyResponseListener listener) {
        String url = "https://jeanvw.000webhostapp.com/actions/isInFavorites.php";

        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void updateFavorite(String action, String city, final VolleyResponseListener listener) {
        String url = "https://jeanvw.000webhostapp.com/actions/updateFavorite.php";

        Map<String, String> params = new HashMap<>();
        params.put("action", action);
        params.put("city", city);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void getAllFavorite(final VolleyResponseListener listener) {
        String url = "https://jeanvw.000webhostapp.com/actions/getAllFavorites.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }
}
