package com.example.dejan.weatherforcast;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;import java.lang.Math;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;


public class CityList extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    List<Weather> myList ;
    ListView cityList;
    String location;
    ArrayAdapter<Weather> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);
        myList= read();
        cityList= (ListView)findViewById(R.id.citylist);
        adapter = new MyListAdapter(this,R.layout.daliylistview,myList);
        cityList.setAdapter(adapter);
        cityList.setOnItemClickListener(this);
        cityList.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_city_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.newCity) {
            Intent intent = new Intent(CityList.this, NewCity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.newCityGPS) {
            Intent intent = new Intent(CityList.this, StartApp.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(CityList.this,WeatherForecast.class);
        location=myList.get(position).currentWeather.getName();

        String sLo= String.valueOf(myList.get(position).currentWeather.getLon());
        String sLa=String.valueOf(myList.get(position).currentWeather.getLat());
        String [] gps={sLa,sLo};

        intent.putExtra("gpsCord", gps);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to delete item");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                myList.remove(position);
                saveData(myList);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();

        return true;
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

    private class MyListAdapter extends ArrayAdapter<Weather> {

        ArrayList<Weather> list;
        LayoutInflater inflater;

        public MyListAdapter (Context context, int resource, List objects) {
            super(context, resource, objects);
            list= (ArrayList<Weather>)objects;
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.citylistview, parent, false);
            }

            Weather weather =list.get(position);

            TextView city= (TextView) convertView.findViewById(R.id.citylist);
            TextView temp = (TextView) convertView.findViewById(R.id.temp_listview);
            TextView description= (TextView) convertView.findViewById(R.id.descriptionlist);

            city.setText(weather.currentWeather.getName());
            city.setTextColor(Color.BLACK);
            temp.setText(Math.round((weather.currentWeather.getTemp())- 273.15) + "°c");
            temp.setTextColor(Color.BLACK);
            description.setText(weather.currentWeather.getDescription());
            description.setTextColor(Color.BLACK);

            return convertView;
        }
    }


}
