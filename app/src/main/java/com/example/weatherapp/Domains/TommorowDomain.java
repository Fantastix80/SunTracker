package com.example.weatherapp.Domains;

public class TommorowDomain {
    private String day;
    private String picPath;
    private int highTemp;
    private int lowTemp;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    public TommorowDomain(String day, String picPath, int highTemp, int lowTemp) {
        this.day = day;
        this.picPath = picPath;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }
}
