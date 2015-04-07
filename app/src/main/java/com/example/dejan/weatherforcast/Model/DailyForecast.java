package com.example.dejan.weatherforcast.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Dejan on 2/23/2015.
 */
public class DailyForecast implements Serializable {

    private long day;
    private int max;
    private int min;
    private Bitmap img;;
    private String icon;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }


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