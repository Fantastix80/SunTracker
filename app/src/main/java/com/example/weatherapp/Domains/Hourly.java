package com.example.weatherapp.Domains;

public class Hourly {
    private String Hour;
    private int temp;
    private String picPath;

    public Hourly(String hour, int temp, String picPath) {
        this.Hour = hour;
        this.temp = temp;
        this.picPath = picPath;
    }

    public String getHour() {
        return Hour;
    }

    public int getTemp() {
        return temp;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

}
