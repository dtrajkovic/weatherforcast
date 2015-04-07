package com.example.dejan.weatherforcast;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Dejan on 4/7/2015.
 */
public class ImageReturn {

    public void ReturnBackground(Context context,RelativeLayout layout,String s){


        switch (s){

            case "01d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.clearsky));
                break;
            case "02d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.fewclouds));
                break;
            case "03d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.scatteredclouds));
                break;
            case "04d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.brokenclouds));
                break;
            case "09d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.showerrain));
                break;
            case "10d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.rain));
                break;
            case "11d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.thunderstorm));
                break;
            case "13d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.snow));
                break;
            case "50d" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.mist));
                break;
            case "01n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.clearsky));;
                break;
            case "02n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.fewclouds));
                break;
            case "03n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.scatteredclouds));
                break;
            case "04n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.brokenclouds));
                break;
            case  "09n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.showerrain));
                break;
            case "10n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.rain));
                break;
            case "11n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.thunderstorm));
                break;
            case "13n" :
                layout.setBackground(context.getResources().getDrawable(R.drawable.mist));
                break;
        }
    }
    public  void ReturnIcon(ImageView icon,String s){
        switch (s){

            case "01d" :
                icon.setImageResource(R.drawable.a);
                break;
            case "02d" :

                icon.setImageResource(R.drawable.a02d);
                break;
            case "03d" :
                icon.setImageResource(R.drawable.a03d);
                break;
            case "04d" :
                icon.setImageResource(R.drawable.a04d);
                break;
            case "09d" :
                icon.setImageResource(R.drawable.a09d);
                break;
            case "10d" :
                icon.setImageResource(R.drawable.a10d);
                break;
            case "11d" :
                icon.setImageResource(R.drawable.a11d);
                break;
            case "13d" :
                icon.setImageResource(R.drawable.a13d);
                break;
            case "50d" :
                icon.setImageResource(R.drawable.a50d);
                break;
            case "a01n" :
                icon.setImageResource(R.drawable.a01n);
                break;
            case "a02n" :
                icon.setImageResource(R.drawable.a02n);
                break;
            case "a03n" :
                icon.setImageResource(R.drawable.a03n);
                break;
            case "a04n" :
                icon.setImageResource(R.drawable.a04n);
                break;
            case "a09n" :
                icon.setImageResource(R.drawable.a09n);
                break;
            case "a10n" :
                icon.setImageResource(R.drawable.a10n);
                break;
            case "a11n" :
                icon.setImageResource(R.drawable.a11n);
                break;
            case "a13n" :
                icon.setImageResource(R.drawable.a13n);
        }
    }
}
