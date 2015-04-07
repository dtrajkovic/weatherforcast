package com.example.dejan.weatherforcast.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.dejan.weatherforcast.Gps.GPSTracker;
import com.example.dejan.weatherforcast.R;
import com.example.dejan.weatherforcast.Model.Weather;

import io.fabric.sdk.android.Fabric;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;



public class StartApp extends ActionBarActivity {
    List<Weather> myList;
    GPSTracker gps;
    EditText editLong,editLat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.start_app);
        getSupportActionBar().setTitle(R.string.gps_tracker);

        myList=read();




        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Do you want to use GPS cordinates to see weather");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                gps = new GPSTracker(StartApp.this);
                editLong= (EditText) findViewById(R.id.editTextLong);
                editLat= (EditText) findViewById(R.id.editTextLat);
                if(gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    
                    editLong.setText(String.valueOf(longitude) );
                    editLat.setText(String.valueOf(latitude));


                } else {
                    gps.showSettingsAlert();
                }
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(myList.size()==0) {
                    Intent intent = new Intent(StartApp.this, NewCity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(StartApp.this, CityList.class);
                    startActivity(intent);
                    finish();
                }
                dialog.dismiss();
            }
        });



        alert.show();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_start_app, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.citylistfromGPS) {
            Intent intent = new Intent(this, CityList.class);
            startActivity(intent);

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

    public void search(View view) {
        if(view.getId()==(R.id.submit)){
            String sLo=editLong.getText().toString();
            String sLa=editLat.getText().toString();
            String [] gps={sLa,sLo};

            if(sLa.equals("")||sLo.equals("")){
                Toast.makeText(this,"YOU MUST ENTER GPS CORDINATES",Toast.LENGTH_LONG).show();

            }else{

                Intent intent = new Intent(StartApp.this, WeatherForecast.class);
                intent.putExtra("gpsCord",gps);
                startActivity(intent);
                finish();}

        }

         if(view.getId()==(R.id.curentGPS)){
            gps = new GPSTracker(StartApp.this);
            editLong= (EditText) findViewById(R.id.editTextLong);
            editLat= (EditText) findViewById(R.id.editTextLat);

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                editLong.setText(String.valueOf(longitude) );
                editLat.setText(String.valueOf(latitude));
        }
    }



}
