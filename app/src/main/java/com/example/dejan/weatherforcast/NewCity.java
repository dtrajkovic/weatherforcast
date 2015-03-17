package com.example.dejan.weatherforcast;


import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class NewCity extends ActionBarActivity {
    AutoCompleteTextView city;
    Button search;
    String location;
    ArrayList<City> cityList= new ArrayList<>();
    String[] cityname;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_city);
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute();
        city = ( AutoCompleteTextView) findViewById(R.id.autoCity);
        search = (Button) findViewById(R.id.submit);
    }

    public void putCity(ArrayList<City>city){
        cityname=new String[city.size()];
        for (int i = 0; i < city.size(); i++) {
            String s=city.get(i).getName();
            cityname[i]= s;
        }
    }


    public void search(View view) {
        location = city.getText().toString();
        if(location.equals("")){
            Toast.makeText(this,"YOU MUST ENTER NAME OF CITY",Toast.LENGTH_LONG).show();

        }else{
            Intent intent = new Intent(NewCity.this, WeatherForecast.class);
            intent.putExtra("city", location);
            startActivity(intent);
            finish();}

    }

    public String readJson(){

        AssetManager manager =getAssets();
        InputStream input;
        String json=null;
        try{
            input=manager.open("filee.json");
            int size=input.available();
            byte[] buffer=new byte[size];
            input.read(buffer);
            input.close();
            json=new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, ArrayList<City>> {
        String list;

        @Override
        protected ArrayList<City> doInBackground(String... params) {
            list=readJson();
            try {
                cityList=JsonWeatherPars.getCity(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            putCity(cityList);
            adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,cityname);
            return cityList;
        }

        @Override
        protected void onPostExecute(ArrayList<City> cities) {
            super.onPostExecute(cities);
            city.setAdapter(adapter);


        }
    }

}
