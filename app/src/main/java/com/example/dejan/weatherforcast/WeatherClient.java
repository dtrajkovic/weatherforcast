package com.example.dejan.weatherforcast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Dejan on 2/19/2015.
 */
public class WeatherClient {

    private static String CURRENT_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String API_KEY = "&APPID=8fa6f3cbeaf8435fe54c2d03a93e4342";
    private static String DAILY_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static String HOURLY_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";

    public String getCurrentWeather(String location) {
        URL url;
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            url = new URL(CURRENT_URL + location + API_KEY);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            //citanje odgovora
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line + "\r\n");
            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (Throwable t) {
            Log.d("WeatherclientLog", t.getMessage());
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return "";
    }


    public String getDailyWeather(String location) {
        URL url;
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            url = new URL(DAILY_URL + location + API_KEY);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            //citanje odgovora
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line + "\r\n");
            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (Throwable t) {
            Log.d("WeatherclientLog", t.getMessage());
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return "";
    }

    public String getHaurlyWeather(String location) {
        URL url;
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            url = new URL(HOURLY_URL + location + API_KEY);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            //citanje odgovora
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line + "\r\n");
            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (Throwable t) {
            Log.d("WeatherclientLog", t.getMessage());
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return "";
    }

}
