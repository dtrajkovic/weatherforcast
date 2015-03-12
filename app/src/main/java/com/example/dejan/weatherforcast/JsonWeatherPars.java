package com.example.dejan.weatherforcast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dejan on 2/19/2015.
 */
public class JsonWeatherPars {


    public static CurrentWeather getCurrentWeather(String data) throws JSONException {

        CurrentWeather weather = new CurrentWeather();
        JSONObject jObj = new JSONObject(data);
        weather.setName(getString("name", jObj));
        weather.setDay(getLong("dt",jObj));
        JSONArray current = jObj.getJSONArray("weather");
        JSONObject desJSON = current.getJSONObject(0);
        weather.setDescription(getString("description", desJSON));
        weather.setIcon(getString("icon",desJSON));
        JSONObject tempMinMax = getObject("main", jObj);
        weather.setTemp(getInt("temp", tempMinMax));
        weather.setTempMin(getFloat("temp_min", tempMinMax));
        weather.setTempMax(getFloat("temp_max", tempMinMax));

        return weather;
    }


    public static ArrayList getDaliyWeather(String data) throws JSONException {


        ArrayList<DailyForecast> myList = new ArrayList<>();
        JSONObject jObj = new JSONObject(data);
        JSONArray current = jObj.getJSONArray("list");
        for (int i = 0; i < current.length(); i++) {
            DailyForecast weather = new DailyForecast();
            JSONObject desJSON = current.getJSONObject(i);
            weather.setDay(getLong("dt", desJSON));
            JSONObject main = getObject("temp", desJSON);
            weather.setMax(getInt("max", main));
            weather.setMin(getInt("min", main));
            JSONArray wet= desJSON.getJSONArray("weather");
            for (int j = 0; j < wet.length(); j++) {
                JSONObject wetJson= wet.getJSONObject(j);
                weather.setDesc(getString("description",wetJson));
                weather.setIcon(getString("icon",wetJson));

            }
            myList.add(weather);
        }
        return myList;
    }

    public static ArrayList getHourlyWeather(String data) throws JSONException {

        ArrayList<HourlyForecast> myList = new ArrayList<>();
        JSONObject jObj = new JSONObject(data);
        JSONArray current = jObj.getJSONArray("list");
        for (int i = 0; i < current.length(); i++) {
            HourlyForecast weather = new HourlyForecast();
            JSONObject desJSON = current.getJSONObject(i);
            long a =getLong("dt", desJSON);
            weather.setTime(a*1000);
            JSONObject main = getObject("main", desJSON);
            weather.setTemp(getInt("temp", main));
            Date date = new Date(a*1000);
            weather.setConvertTime(""+new SimpleDateFormat("HH").format(date));
            JSONArray wet= desJSON.getJSONArray("weather");
            for (int j = 0; j < wet.length(); j++) {
                JSONObject wetJson= wet.getJSONObject(j);
                weather.setIcon(getString("icon",wetJson));

            }
            myList.add(weather);
        }

        return myList;
    }



    public static ArrayList getCity(String data) throws JSONException {

        ArrayList<City> myList = new ArrayList<>();
        String a=data;
        JSONArray current = new JSONArray(data);
        for (int i = 0; i < current.length(); i++) {
            JSONObject desJSON = current.getJSONObject(i);
            City city= new City();
            city.setName(getString("nm", desJSON));
            city.setLat(getFloat("lat",desJSON));
            city.setLon(getFloat("lon",desJSON));
            myList.add(city);
        }
        return myList;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        String a = jObj.getString(tagName);
        return a;
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
    private static long getLong(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getLong(tagName);
    }


}
