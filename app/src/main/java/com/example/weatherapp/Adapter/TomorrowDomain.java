package com.example.weatherapp.Adapter;

public class TomorrowDomain {
    private String day, picPath, highTemp, lowTemp;

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

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public TomorrowDomain(String day, String picPath, String highTemp, String lowTemp) {
        this.day = day;
        this.picPath = picPath;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }
}
