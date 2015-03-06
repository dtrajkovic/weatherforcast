package com.example.dejan.weatherforcast;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NewCity extends ActionBarActivity {
    AutoCompleteTextView city;
    Button search;
    String location;
    StartApp startApp = new StartApp();

    ArrayList<City> cityList= new ArrayList<>();

    String[] cityname;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_app);

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute();

       /// putCity();

        city = ( AutoCompleteTextView) findViewById(R.id.city);

        search = (Button) findViewById(R.id.submit);


    }

    public void putCity(ArrayList<City>city){
        cityname=new String[city.size()];
        for (int i = 0; i < city.size(); i++) {
            String s=city.get(i).getName();
            cityname[i]= s;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void search(View view) {
        Intent intent = new Intent(NewCity.this, WeatherForecast.class);
        location = city.getText().toString();
        intent.putExtra("city", location);
        startActivity(intent);
        finish();
    }




    public List<Weather> read(){

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
    public String readJson(){

        // List<Weather> list =new ArrayList<Weather>();
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
        ArrayList<String> o = new ArrayList<>();



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
