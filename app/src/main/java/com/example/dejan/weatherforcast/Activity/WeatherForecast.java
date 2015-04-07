package com.example.dejan.weatherforcast.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dejan.weatherforcast.Fragments.HourlyFragment;
import com.example.dejan.weatherforcast.Fragments.DalyFragment;
import com.example.dejan.weatherforcast.HttpClient.WeatherClientCity;
import com.example.dejan.weatherforcast.HttpClient.WeatherClientGPS;
import com.example.dejan.weatherforcast.ImageReturn;
import com.example.dejan.weatherforcast.JsonPars.JsonWeatherPars;
import com.example.dejan.weatherforcast.Model.CurrentWeather;
import com.example.dejan.weatherforcast.Model.DailyForecast;
import com.example.dejan.weatherforcast.Model.HourlyForecast;
import com.example.dejan.weatherforcast.Model.Weather;
import com.example.dejan.weatherforcast.R;
import com.example.dejan.weatherforcast.View.HourlyForecastView;

import org.json.JSONException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.lang.Math;
import java.lang.String;
import java.lang.Void;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WeatherForecast extends ActionBarActivity {
    TextView cityName, descriprion, temp, day,minTempt,maxTempt;
    String location,sLo,sLa;
    Double lat,lon;
    boolean gps,city=true;
    CurrentWeather curentweather = new CurrentWeather();
    ArrayList<DailyForecast> daliyweather = new ArrayList<>();
    ArrayList<HourlyForecast> hourlyForecast= new ArrayList<>();
    DalyFragment fragment_2;
    HourlyFragment fragment1;
    Weather tepmWeather;
    List<Weather> cityList = new ArrayList<>();
    private GestureDetectorCompat gestureDetectorCompat;
    RelativeLayout layout;
    LinearLayout mLinearLayourHourlyForecasts;
    ImageReturn mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather__forcast);
        cityName = (TextView) findViewById(R.id.city_name);
        descriprion = (TextView) findViewById(R.id.descriprion);
        temp = (TextView) findViewById(R.id.temp);
        day = (TextView) findViewById(R.id.curentDay);
        maxTempt = (TextView) findViewById(R.id.maxTemp);
        minTempt = (TextView) findViewById(R.id.minTemp);
        fragment_2 = (DalyFragment)getFragmentManager().findFragmentById(R.id.fragment2);
        fragment1= (HourlyFragment)getFragmentManager().findFragmentById(R.id.fragment);
        mLinearLayourHourlyForecasts = (LinearLayout) findViewById(R.id.horizontalLayoutForHourlyForecasts);
        mImage=new ImageReturn();
        Intent intent = getIntent();
        location = intent.getStringExtra("city");
        if(!(location==null)){
            JSONWeatherTask task = new JSONWeatherTask(this);
            task.execute(new String[]{location});
            city=false;
        }else{
            gps=false;
            String [] gpsArr=intent.getStringArrayExtra("gpsCord");
            sLa=gpsArr[0];
            sLo=gpsArr[1];
            lat=Double.parseDouble(sLa);
            lon= Double.parseDouble(sLo);
            JSONWeatherTaskGPS task = new JSONWeatherTaskGPS(this);
            task.execute(new Double[]{lat,lon});

        }
        cityList=read();

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
    }

    public void  curent(Weather weather){

        tepmWeather=weather;
        String newTemp = "" + Math.round((weather.currentWeather.getTemp() - 273.15)) + "°c";
        String maxTemp =  Math.round((weather.currentWeather.getTempMax() - 273.15)) + "°c , ";
        String minTemp =  Math.round((weather.currentWeather.getTempMin() - 273.15)) + "°c";
        cityName.setText(weather.currentWeather.getName());
        cityName.setTextColor(getResources().getColor(R.color.text_color));
        temp.setText(newTemp);
        temp.setTextColor(getResources().getColor(R.color.text_color));
        descriprion.setText(weather.currentWeather.getDescription());
        descriprion.setTextColor(getResources().getColor(R.color.text_color));
        Date date = new Date ((weather.currentWeather.getDay()*1000));
        day.setText("" + new SimpleDateFormat("EEEE").format(date));
        maxTempt.setText(maxTemp);
        maxTempt.setTextColor(getResources().getColor(R.color.text_color));
        minTempt.setText(minTemp);
        day.setTextColor(getResources().getColor(R.color.text_color));
        layout= (RelativeLayout) findViewById(R.id.weatherforcast);
        mImage.ReturnBackground(this,layout,weather.currentWeather.getIcon());


    }

    public void Daily(Weather weather){

            MyListAdapter files = new MyListAdapter(this, R.layout.daliylistview, weather.dailyForecasts);
            fragment_2.days.setAdapter(files);
         }

    public  void Hourly(Weather weather){

        for (int i = 0; i < weather.hourlyForecasts.size(); i++){
            mLinearLayourHourlyForecasts.addView(new HourlyForecastView(this,weather.hourlyForecasts.get(i)));

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather__forcast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.listcity) {
            Intent intent = new Intent(WeatherForecast.this, CityList.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.addCity) {
            Intent intent = new Intent(WeatherForecast.this, NewCity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void putCity( Weather w){

            if(cityList.size()==0){
                cityList.add(w);
                saveData(cityList);
            }else{

            for (int i =0; i<cityList.size();i++) {

                double olo=w.currentWeather.getLon();
                double ola= w.currentWeather.getLat();

                double nlo= cityList.get(i).currentWeather.getLon();
                double nla= cityList.get(i).currentWeather.getLat();

                if ((olo==nlo)||(ola==nla) ) {
                    cityList.remove(cityList.get(i));
                    cityList.add(i,w);
                    break;
                } else {
                        if(i==(cityList.size()-1)) {
                            cityList.add(w);
                        }
                    }

            }

            saveData(cityList);}
        }

    public void saveData(List<Weather> list){

            String fileName ="save.txt";
            Context context = getApplicationContext();
            FileOutputStream fos;
            ObjectOutputStream stream;
            try
            {   fos = context.openFileOutput( fileName, MODE_PRIVATE);
                stream = new ObjectOutputStream(fos);

                try
                {
                    stream.writeObject(list);
                    stream.flush();
                    stream.close();
                    fos.close();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public List<Weather>  read(){

            List<Weather> list =new ArrayList<Weather>();
            String file ="save.txt";
            Context context = getApplicationContext();
            FileInputStream fis;
            ObjectInputStream stream;
            try {
                fis = context.openFileInput( file );

                stream = new ObjectInputStream(fis);
                list = (List<Weather> )stream.readObject();
                stream.close();



            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            return list;
        }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
        Weather weather = new Weather();
        WeatherForecast context;
        LinearLayout layoutProgresBar = (LinearLayout) findViewById(R.id.layoutProgresBar);

        public JSONWeatherTask(final WeatherForecast context) {
            super();

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            layoutProgresBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Weather doInBackground(String... params) {

                 String curent = ((new WeatherClientCity()).getCurrentWeather(params[0]));
                 String daliy = ((new WeatherClientCity()).getDailyWeather(params[0]));
                 String hourly=((new WeatherClientCity()).getHaurlyWeather(params[0]));

                 if((curent.equals("")||daliy.equals("")||hourly.equals(""))){
                     return null; }
                 else {
                     try {
                         curentweather = JsonWeatherPars.getCurrentWeather(curent);
                         daliyweather = JsonWeatherPars.getDaliyWeather(daliy);
                         hourlyForecast= JsonWeatherPars.getHourlyWeather(hourly);
                         weather.currentWeather = curentweather;
                         weather.dailyForecasts = daliyweather;
                         weather.hourlyForecasts=hourlyForecast;
                         putCity(weather);
                         return weather;

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
                 return null;



        }


        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

        if(weather == null) {

            AlertDialog.Builder alert = new AlertDialog.Builder(WeatherForecast.this);
            alert.setTitle("make sure that you have internet connection");
            alert.setMessage("TRY AGAIN");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    JSONWeatherTask task = new JSONWeatherTask(WeatherForecast.this);
                    task.execute(new String[]{location});
                    dialog.dismiss();

                }
            });
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.dismiss();
                }
            });



            alert.show();
        } else {
            curent(weather);
            Daily(weather);
            Hourly(weather);
        }
            layoutProgresBar.setVisibility(View.GONE);

        }}


    private class JSONWeatherTaskGPS extends AsyncTask<Double, Void, Weather> {
        Weather weather = new Weather();
        WeatherForecast context;
        LinearLayout layoutProgresBar = (LinearLayout) findViewById(R.id.layoutProgresBar);

        public JSONWeatherTaskGPS(final WeatherForecast context) {
            super();

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            layoutProgresBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Weather doInBackground(Double... params) {
            String curent = ((new WeatherClientGPS()).getCurrentWeather(params[0],params[1]));
            String daliy = ((new WeatherClientGPS()).getDailyWeather(params[0], params[1]));
            String hourly= ((new WeatherClientGPS()).getHaurlyWeather(params[0], params[1]));

            if((curent.equals("")||daliy.equals("")||hourly.equals(""))){
                return null; }
            else {
                try {
                    curentweather = JsonWeatherPars.getCurrentWeather(curent);
                    daliyweather = JsonWeatherPars.getDaliyWeather(daliy);
                    hourlyForecast= JsonWeatherPars.getHourlyWeather(hourly);
                    weather.currentWeather = curentweather;
                    weather.dailyForecasts = daliyweather;
                    weather.hourlyForecasts=hourlyForecast;
                    putCity(weather);
                    return weather;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;


        }
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            if(weather == null) {

                AlertDialog.Builder alert = new AlertDialog.Builder(WeatherForecast.this);
                alert.setTitle("make sure that you have internet connection");
                alert.setMessage("TRY AGAIN");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       JSONWeatherTaskGPS task = new JSONWeatherTaskGPS(WeatherForecast.this);
                        task.execute(new Double[]{lat,lon});
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
                alert.show();
            } else {
                curent(weather);
                Daily(weather);
                Hourly(weather);
            }
            layoutProgresBar.setVisibility(View.GONE);

        }}


    public class MyListAdapter extends ArrayAdapter {

            ArrayList<DailyForecast> list;
            LayoutInflater inflater;

        public MyListAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            list = (ArrayList<DailyForecast>) objects;
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.daliylistview, parent, false);
            }

            TextView max = (TextView) convertView.findViewById(R.id.max);
            TextView min = (TextView) convertView.findViewById(R.id.min);
            TextView day = (TextView) convertView.findViewById(R.id.day);
            ImageView icon= (ImageView)convertView.findViewById(R.id.icon_dalylist);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);

            Date date = new Date ((list.get(position).getDay()*1000));

            max.setText(Math.round((list.get(position).getMax())- 273.15) + "°c");
            max.setTextColor(getResources().getColor(R.color.text_color));

            min.setText(Math.round((list.get(position).getMin())- 273.15) + "°c");
            min.setTextColor(getResources().getColor(R.color.text_color_2));

            day.setText(""+new SimpleDateFormat("EEEE").format(date));
            day.setTextColor(getResources().getColor(R.color.text_color));

            desc.setText(list.get(position).getDesc());
            desc.setTextColor(getResources().getColor(R.color.text_color));

            mImage.ReturnIcon(icon,list.get(position).getIcon());

            return convertView;
        }
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,float velocityX, float velocityY) {


                int i=cityList.lastIndexOf(tepmWeather);

                if(event2.getX() < event1.getX()) {
                    i--;
                    if(i<0){
                        i=cityList.size()-1;
                    }
                    String cityName=cityList.get(i).currentWeather.getName();
                    double lo= cityList.get(i).currentWeather.getLon();
                    double la=cityList.get(i).currentWeather.getLat();
                    JSONWeatherTaskGPS task = new JSONWeatherTaskGPS(WeatherForecast.this);
                    task.execute(new Double[]{la,lo});
                }
                if(event2.getX() > event1.getX()) {
                    i++;
                    if (i>cityList.size()-1) {
                        i=0;
                    }
                    String cityName=cityList.get(i).currentWeather.getName();
                    double lo= cityList.get(i).currentWeather.getLon();
                    double la=cityList.get(i).currentWeather.getLat();
                    JSONWeatherTaskGPS task = new JSONWeatherTaskGPS(WeatherForecast.this);
                    task.execute(new Double[]{la,lo});
                }
            return true;
        }
    }
}
