package com.example.weatherapp.Activitis;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Adapter.HourlyAdapter;
import com.example.weatherapp.Domains.Hourly;
import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.manager.APIManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;
    private ActivityMainBinding binding;
    protected LocationManager locationManager;
    private Location currentLocation;
    private APIManager apiManager;
    boolean askPlayerLocation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.navigation_favorite:
                    replaceFragment(new FavoriteFragment());
                    break;
            }
            return true;
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Dans le cas où l'utilisateur viendrait à refuser de partager sa localisation, on vient faire un early return.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        apiManager = new APIManager(getApplicationContext());

        // Initialisation de la RecyclerView après la vérification des autorisations
        initRecyclerView();
    }


    private void initRecyclerView() {
        ArrayList<Hourly> items = new ArrayList<>();
        items.add(new Hourly("10 pm", 28,"cloudy"));
        items.add(new Hourly("11 pm", 29,"sun"));
        items.add(new Hourly("12 pm", 30,"wind"));
        items.add(new Hourly("1 am", 29,"rainly"));
        items.add(new Hourly("2 am", 27,"storm"));

        recyclerView=findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterHourly = new HourlyAdapter(items);
        Log.d("Adapter" + adapterHourly.item;
        recyclerView.setAdapter(adapterHourly);

        Log.d("RecyclerView", "Item count: " + items.size());
        Log.d("RecyclerView", "Adapter: " + recyclerView.getAdapter());

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        if (askPlayerLocation) {
            //on définie notre listener qui va s'activer lorsque l'api aura répondu à notre requête
            APIManager.VolleyResponseListener listener = new APIManager.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String country = response.getString("countryName");
                        String city = response.getString("city");
                        System.out.println("Country: " + country + " | city: " + city);
                        //Appel API météo
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            };

            // On vient récupérer la ville où se situe l'utilisateur.
            apiManager.getCityFromLocation(location, listener);
            askPlayerLocation = false;
        }
    }

}