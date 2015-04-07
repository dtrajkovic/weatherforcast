package com.example.dejan.weatherforcast.HttpClient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dejan on 3/17/2015.
 */
public class WeatherClientGPS {

    private static String CURRENT_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static String API_KEY = "&APPID=8fa6f3cbeaf8435fe54c2d03a93e4342";
    private static String DAILY_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static String HOURLY_URL = "http://api.openweathermap.org/data/2.5/forecast?";

    public String getCurrentWeather(double lat,double lon ) {

        String a="lon="+String.valueOf(lon);
        String b="lat="+String.valueOf(lat);
        URL url;
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            url = new URL(CURRENT_URL + b+"&"+a + API_KEY);
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


    public String getDailyWeather(double lat,double lon) {

        String a="lon="+String.valueOf(lon);
        String b="lat="+String.valueOf(lat);
        URL url;
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            url = new URL(DAILY_URL  + b+"&"+a + API_KEY);
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

    public String getHaurlyWeather(double lat,double lon) {

        String a="lon="+String.valueOf(lon);
        String b="lat="+String.valueOf(lat);
        URL url;
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            url = new URL(HOURLY_URL+ b+"&"+a +API_KEY);
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
