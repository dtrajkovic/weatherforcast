package com.example.dejan.weatherforcast;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class StartApp extends ActionBarActivity {
    List<Weather> myList;

    ArrayList<City> city= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.start_app);


        myList=read();
        // setContentView(R.layout.searchcity);
        if(myList.size()==0) {
            Intent intent = new Intent(StartApp.this, NewCity.class);

            startActivity(intent);
        }else{
            Intent intent = new Intent(StartApp.this, CityList.class);

            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_app, menu);
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






}
