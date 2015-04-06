package com.whatsup.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsup.R;
import com.whatsup.asynctask.DownloadImageTask;
import com.whatsup.asynctask.LocalWeatherAsyncTask;
import com.whatsup.db.CurrentWeatherDb;
import com.whatsup.db.ForecastDb;
import com.whatsup.db.RequestDb;
import com.whatsup.model.CurrentConditionElement;
import com.whatsup.model.DataElement;
import com.whatsup.model.RequestElement;
import com.whatsup.model.WeatherElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherDetail extends Activity {

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        query = getIntent().getStringExtra("query");

        boolean isAdd = getIntent().getBooleanExtra("isAdd", false);

        if (isAdd) {
            new LocalWeatherAsyncTask(this).execute(query);
        } else {
            setContentView(R.layout.activity_weather_detail);
            showSelectedCity(query);
        }
    }


    private void showSelectedCity(String query) {
        CurrentWeatherDb cdb = new CurrentWeatherDb(this);
        ForecastDb fdb = new ForecastDb(this);

        CurrentConditionElement currentConditionElement = cdb.getCurrentWeather(query);
        List<WeatherElement> listForecast = fdb.getAllForecast(query);

        processData(currentConditionElement, listForecast);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.refresh) {
            refresh(query);
            return true;
        }
        return false;

    }

    private void refresh(String query) {
        new LocalWeatherAsyncTask(this, "refresh").execute(query);
    }

    private void processData(CurrentConditionElement currentConditionElement, List<WeatherElement> weatherElementList) {
        //Query
        TextView place = (TextView) findViewById(R.id.tvPlace);
        place.setText(currentConditionElement.getQuery());

        //Weather Description
        TextView weatherDesc = (TextView) findViewById(R.id.tvWeatherDesc);
        weatherDesc.setText(currentConditionElement.getWeatherDescription());

        //TEMP C
        TextView tempC = (TextView) findViewById(R.id.tvTemp);
        Character deg = '\u00B0';
        tempC.setText(currentConditionElement.getTempC() + deg);

        //Humidity
        TextView humidity = (TextView) findViewById(R.id.tvHumidity);
        humidity.setText(currentConditionElement.getHumidity() + "%");

        //Wind
        TextView wind = (TextView) findViewById(R.id.tvWind);
        wind.setText(currentConditionElement.getWindDirection16Point() + " at " +
                currentConditionElement.getWindSpeedMiles() + " mph");

        //Cloud cover
        TextView cc = (TextView) findViewById(R.id.tvCloudCover);
        cc.setText(currentConditionElement.getCloudCover() + "%");

        //Precip
        TextView precip = (TextView) findViewById(R.id.tvPrecip);
        precip.setText(currentConditionElement.getPrecipitationMM() + "%");


        //iconURL
        ImageView imgView = (ImageView) findViewById(R.id.imgCurrent);
        new DownloadImageTask(imgView).execute(currentConditionElement.getWeatherIconUrl());


        //FORECAST
        LinearLayout forecast = (LinearLayout) findViewById(R.id.linForecast);


        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        SimpleDateFormat main = new SimpleDateFormat("yyyy-MM-dd");

        for (WeatherElement we : weatherElementList) {

            try {
                LinearLayout loadDay = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.day, null);
                GradientDrawable background = (GradientDrawable) loadDay.getBackground();


                TextView tvDay = (TextView) loadDay.findViewById(R.id.tvD1Date);
                TextView tvMin = (TextView) loadDay.findViewById(R.id.tvD1minC);
                TextView tvMax = (TextView) loadDay.findViewById(R.id.tvD1maxC);

                tvMin.setText(we.getTempMinC() + deg + "c");
                tvMax.setText(we.getTempMaxC() + deg + "c");

                ImageView d1Img = (ImageView) loadDay.findViewById(R.id.d1Img);
                String reformattedStr = sdf.format(main.parse(we.getDate())).toUpperCase();
                tvDay.setText(reformattedStr);

                new DownloadImageTask(d1Img).execute(we.getWeatherIconUrl());

                forecast.addView(loadDay);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
