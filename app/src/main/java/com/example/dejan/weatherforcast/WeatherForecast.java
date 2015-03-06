package com.example.dejan.weatherforcast;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dejan.weatherforcast.R;import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;import java.lang.Math;import java.lang.Override;import java.lang.String;import java.lang.Void;import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WeatherForecast extends ActionBarActivity {
    TextView cityName, descriprion, temp, day;
    String location;
    CurrentWeather curentweather = new CurrentWeather();
    ArrayList<DailyForecast> daliyweather = new ArrayList<>();
    ArrayList<HourlyForecast> hourlyForecast= new ArrayList<>();
    Fragment_2 fragment_2;
    Fragment_1 fragment1;
    Weather tepmWeather;
    List<Weather> cityList = new ArrayList<>();
    private GestureDetectorCompat gestureDetectorCompat;


    //ListView days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather__forcast);
        cityName = (TextView) findViewById(R.id.city_name);
        descriprion = (TextView) findViewById(R.id.descriprion);
        temp = (TextView) findViewById(R.id.temp);
        day = (TextView) findViewById(R.id.day);



        fragment_2 = (Fragment_2)getFragmentManager().findFragmentById(R.id.fragment2);
        fragment1= (Fragment_1)getFragmentManager().findFragmentById(R.id.fragment);

        Intent intent = getIntent();
        location = intent.getStringExtra("city");
        cityList=read();

        JSONWeatherTask task = new JSONWeatherTask(this);
        task.execute(new String[]{location});
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());



    }





    public void  curent(Weather weather){

        tepmWeather=weather;
        String newTemp = "" + Math.round((weather.currentWeather.getTemp() - 273.15)) + "°c";
        String minMax = "TODAY Max Temp:" + Math.round((weather.currentWeather.getTempMax() - 273.15)) + "°c , Min Temp:" + Math.round((weather.currentWeather.getTempMin() - 273.15)) + "°c";
        cityName.setText(weather.currentWeather.getName());
        cityName.setTextColor(Color.BLACK);
        temp.setText(newTemp);
        temp.setTextColor(Color.BLACK);
        descriprion.setText(weather.currentWeather.getDescription());
        descriprion.setTextColor(Color.BLACK);
        day.setText(minMax);
        day.setTextColor(Color.BLACK);

    }

    public void Daily(Weather weather){

             MyListAdapter files = new MyListAdapter(this, R.layout.daliylistview, weather.dailyForecasts);
             fragment_2.days.setAdapter(files);
         }

    public  void Hourly(Weather weather){
        LinearLayout layout = (LinearLayout)findViewById(R.id.horizontalLayout);
        for (int i = 0; i < weather.hourlyForecasts.size(); i++){
            TextView textView = new TextView(getApplicationContext());
            textView.setText(""+weather.hourlyForecasts.get(i));
            textView.setTextColor(Color.BLACK);
            layout.addView(textView);

        }



    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            finish();

            return true;
        }
        if (id == R.id.refresh) {
            JSONWeatherTask task = new JSONWeatherTask(this);
            String s=tepmWeather.currentWeather.getName();
            task.execute(new String[]{s});

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

                String s=w.currentWeather.getName();
                String d= cityList.get(i).currentWeather.getName();

                if (s.equals(d) ) {
                    cityList.remove(cityList.get(i));
                    cityList.add(w);
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



        public JSONWeatherTask(final WeatherForecast context) {
            super();

            this.context = context;
        }


        @Override
        protected Weather doInBackground(String... params) {


            String curent = ((new WeatherClient()).getCurrentWeather(params[0]));
            String daliy = ((new WeatherClient()).getDailyWeather(params[0]));
            String hourly=((new WeatherClient()).getHaurlyWeather(params[0]));

            try {
                curentweather = JsonWeatherPars.getCurrentWeather(curent);
                daliyweather = JsonWeatherPars.getDaliyWeather(daliy);
                hourlyForecast= JsonWeatherPars.getHourlyWeather(hourly);


                weather.currentWeather = curentweather;
                weather.dailyForecasts = daliyweather;
                weather.hourlyForecasts=hourlyForecast;


            } catch (JSONException e) {
                e.printStackTrace();
            }

           putCity(weather);

            return weather;

        }


        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            curent(weather);
            Daily(weather);
            Hourly(weather);
        }




    }

    class MyListAdapter extends ArrayAdapter {

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

            Date date = new Date ((list.get(position).getDay()*1000));
            max.setText("  Max Temp: "+Math.round((list.get(position).getMax())- 273.15) + "°c");
            max.setTextColor(Color.BLACK);
            min.setText("  Min Temp: "+Math.round((list.get(position).getMin())- 273.15) + "°c");
            min.setTextColor(Color.BLACK);
            day.setText(""+new SimpleDateFormat("EEEE").format(date));
            day.setTextColor(Color.BLACK);



            return convertView;
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,float velocityX, float velocityY) {


                int i=cityList.lastIndexOf(tepmWeather);
           // if(!cityList.isEmpty()){

                if(event2.getX() < event1.getX()) {
                    //ulevo

                   i--;
                    if(i<0){
                        i=cityList.size()-1;
                    }

                    curent(cityList.get(i));
                    Daily(cityList.get(i));
                    Hourly(cityList.get(i));

                }
                if(event2.getX() > event1.getX()) {
                    //udesno

                    i++;
                    if (i>cityList.size()-1) {
                        i=0;
                    }

                    curent(cityList.get(i));
                    Daily(cityList.get(i));
                    Hourly(cityList.get(i));

                }




            return true;
        }
    }
}
