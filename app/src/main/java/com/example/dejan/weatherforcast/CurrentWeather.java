package com.example.dejan.weatherforcast;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Dejan on 2/23/2015.
 */
public class CurrentWeather implements Serializable {

    private String name;
    private String description;
    private int temp;
    private float tempMin;
    private float tempMax;
    private String icon;
    private long day;
    private  String convertDay;

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public String getConvertDay() {
        return convertDay;
    }

    public void setConvertDay(String convertDay) {
        this.convertDay = convertDay;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}