package com.example.dejan.weatherforcast;

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