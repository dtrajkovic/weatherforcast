package com.example.dejan.weatherforcast;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Dejan on 3/11/2015.
 */
public class HourlyForecastView extends LinearLayout {

    private TextView time;
    private ImageView icon;
    private TextView temp;

    public HourlyForecastView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hourly_forecast_view, this, true);
    }

    public HourlyForecastView(Context context, HourlyForecast forecastModel) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hourly_forecast_view, this, true);

        time= (TextView) findViewById(R.id.dayAndTime_model);
        icon= (ImageView) findViewById(R.id.icon_model);
        temp= (TextView)findViewById(R.id.temp_model);
        time.setText("   "+forecastModel.getConvertTime()+"h:  ");
        time.setTextColor(Color.BLACK);

        switch (forecastModel.getIcon()){

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
            case "01n" :
                icon.setImageResource(R.drawable.a01n);
                break;
            case "02n" :
                icon.setImageResource(R.drawable.a02n);
                break;
            case "03n" :
                icon.setImageResource(R.drawable.a03n);
                break;
            case "04n" :
                icon.setImageResource(R.drawable.a04n);
                break;
            case "09n" :
                icon.setImageResource(R.drawable.a09n);
                break;
            case "10n" :
                icon.setImageResource(R.drawable.a10n);
                break;
            case "11n" :
                icon.setImageResource(R.drawable.a11n);
                break;
            case "13n" :
                icon.setImageResource(R.drawable.a13n);
                break;
        }
        temp.setText("  "+Math.round(forecastModel.getTemp()- 273.15) + "°c");
        temp.setTextColor(Color.BLACK);
    }

    public HourlyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public HourlyForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

}
