package com.example.dejan.weatherforcast;

import java.io.Serializable;

/**
 * Created by Dejan on 2/23/2015.
 */
public class DailyForecast implements Serializable {

    private long day;
    private int max;
    private int min;

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }


}