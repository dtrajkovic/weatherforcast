package com.example.dejan.weatherforcast.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dejan.weatherforcast.ImageReturn;
import com.example.dejan.weatherforcast.Model.HourlyForecast;
import com.example.dejan.weatherforcast.R;

/**
 * Created by Dejan on 3/11/2015.
 */
public class HourlyForecastView extends LinearLayout {

    private TextView mTime;
    private ImageView mIcon;
    private TextView temp;
    public ImageReturn mImage;

    public HourlyForecastView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hourly_forecast_view, this, true);
    }

    public HourlyForecastView(Context context, HourlyForecast forecastModel) {
        super(context);
        mImage=new ImageReturn();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hourly_forecast_view, this, true);

        mTime = (TextView) findViewById(R.id.dayAndTime_model);
        mIcon = (ImageView) findViewById(R.id.icon_model);
        temp= (TextView)findViewById(R.id.temp_model);

        mTime.setText("   " + forecastModel.getConvertTime() + "h:  ");
        mTime.setTextColor(getResources().getColor(R.color.text_color));

        mImage.ReturnIcon(mIcon, forecastModel.getIcon());


        temp.setText("  " + Math.round(forecastModel.getTemp() - 273.15) + "°c");
        temp.setTextColor(getResources().getColor(R.color.text_color));
    }

    public HourlyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public HourlyForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

}
