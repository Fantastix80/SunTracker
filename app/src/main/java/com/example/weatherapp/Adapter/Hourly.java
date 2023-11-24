package com.example.weatherapp.Adapter;

public class Hourly {
    private String Hour;
    private String temp;
    private String picPath;

    public Hourly(String hour, String temp, String picPath) {
        this.Hour = hour;
        this.temp = temp;
        this.picPath = picPath;
    }

    public String getHour() {
        return Hour;
    }

    public String getTemp() {
        return temp;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

}
