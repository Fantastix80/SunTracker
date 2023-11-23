package com.example.weatherapp.Activitis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView backgroundImageView = view.findViewById(R.id.backgroundImageView);

        // Chargement du GIF avec Glide
        Glide.with(this)
                .load(R.drawable.cloudBackground) // Remplacez "votre_gif" par le nom de votre fichier GIF dans le dossier res/raw/
                .into(backgroundImageView);

        return view;
    }
}*/