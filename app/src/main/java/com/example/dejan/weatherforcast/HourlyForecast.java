package com.example.dejan.weatherforcast;

import java.io.Serializable;

/**
 * Created by Dejan on 2/26/2015.
 */
public class HourlyForecast implements Serializable {
    private int temp;
    private long time;
    private  String convertTime;

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getConvertTime() {
        return convertTime;
    }

    public void setConvertTime(String convertTime) {
        this.convertTime = convertTime;
    }


    @Override
    public String toString() {
        return convertTime+"h" + "\n\n" + Math.round(temp- 273.15) + "°c";
    }
}
