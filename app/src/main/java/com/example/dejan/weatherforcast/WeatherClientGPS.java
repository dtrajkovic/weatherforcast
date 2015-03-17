package com.example.dejan.weatherforcast;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Dejan on 3/17/2015.
 */
public class WeatherClientGPS {

    private static String CURRENT_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static String API_KEY = "&APPID=8fa6f3cbeaf8435fe54c2d03a93e4342";
    private static String DAILY_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    private static String HOURLY_URL = "http://api.openweathermap.org/data/2.5/forecast?";

    public String getCurrentWeather(double lat,double lon ) {

        int i = (int)lat;
        int v=(int)lon;


        String a="lon="+String.valueOf(v);
        String b="lat="+String.valueOf(i);
        String lo= null;
        String la=null;
        try {
            lo = URLEncoder.encode(a, "UTF-8");
            la=URLEncoder.encode(b,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        int i = (int)lat;
        int v=(int)lon;


        String a="lon="+String.valueOf(i);
        String b="lat="+String.valueOf(v);
        String lo= null;
        String la=null;
        try {
            lo = URLEncoder.encode(a, "UTF-8");
            la=URLEncoder.encode(b,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URL url;
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            url = new URL(DAILY_URL   + b+"&"+a + API_KEY);
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
        int i = (int)lat;
        int v=(int)lon;


        String a="lon="+String.valueOf(i);
        String b="lat="+String.valueOf(v);
        String lo= null;
        String la=null;
        try {
            lo = URLEncoder.encode(a, "UTF-8");
            la=URLEncoder.encode(b,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URL url;
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            url = new URL(HOURLY_URL   + b+"&"+a+ API_KEY);
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
