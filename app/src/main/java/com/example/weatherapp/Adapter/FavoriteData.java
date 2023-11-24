package com.example.weatherapp.Adapter;

public class FavoriteData {
    private String city, temperature, weatherIcon, maxDegree, minDegree;

    public FavoriteData(String city, String temperature, String maxDegree, String minDegree, String weatherIcon) {
        this.city = city;
        this.temperature = temperature;
        this.maxDegree = maxDegree;
        this.minDegree = minDegree;
        this.weatherIcon = weatherIcon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public void setMaxDegree(String maxDegree) {
        this.maxDegree = maxDegree;
    }

    public void setMinDegree(String minDegree) {
        this.minDegree = minDegree;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getMaxDegree() {
        return maxDegree;
    }

    public String getMinDegree() {
        return minDegree;
    }
}

