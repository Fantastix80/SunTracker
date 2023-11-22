package com.example.weatherapp.manager;

import android.content.Context;
import android.location.Location;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIManager {
    private Context context;
    public RequestQueue queue;

    public APIManager(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    //Cette méthode permet de récupérer les réponses de l'api
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(JSONObject response);
    }

    public void getCityFromLocation(Location location, final VolleyResponseListener listener) {


        String url = "https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude() + "&localityLanguage=en";

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
