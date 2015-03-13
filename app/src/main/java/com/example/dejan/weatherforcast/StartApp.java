package com.example.dejan.weatherforcast;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;



public class StartApp extends ActionBarActivity {
    List<Weather> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myList=read();
        if(myList.size()==0) {
            Intent intent = new Intent(StartApp.this, NewCity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(StartApp.this, CityList.class);
            startActivity(intent);
            finish();
        }

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
