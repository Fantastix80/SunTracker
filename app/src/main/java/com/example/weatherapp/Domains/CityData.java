package com.example.weatherapp.Domains;

public class CityData {
    private String city;
    private String weatherIcon;
    private int maxDegree;
    private int minDegree;

    public CityData(String city, int maxDegree, int minDegree, String weatherIcon) {
        this.city = city;
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

    public void setMaxDegree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    public void setMinDegree(int minDegree) {
        this.minDegree = minDegree;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public int getMaxDegree() {
        return maxDegree;
    }

    public int getMinDegree() {
        return minDegree;
    }
}

