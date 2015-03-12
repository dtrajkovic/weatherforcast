package com.example.dejan.weatherforcast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dejan on 2/23/2015.
 */
public class Weather implements Serializable {

    public CurrentWeather currentWeather;
    public ArrayList<DailyForecast> dailyForecasts;
    public ArrayList<HourlyForecast> hourlyForecasts;

    public Weather() {
        this.currentWeather = new CurrentWeather();
        this.dailyForecasts = new ArrayList<DailyForecast>();
        this.hourlyForecasts= new ArrayList<HourlyForecast>();

    }

}
