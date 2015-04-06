package com.whatsup.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsup.R;
import com.whatsup.db.CurrentWeatherDb;
import com.whatsup.model.DataElement;
import com.whatsup.model.WeatherElement;
import com.whatsup.parser.LocalWeatherParser;
import com.whatsup.service.LocalWeatherService;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public class LocalWeatherAsyncTask extends AsyncTask<String, Void, DataElement> {

    private LocalWeatherService localWeatherService;
    private Activity context;
    private ProgressDialog dialog;
    private TextView textView;
    private View detail;
    private String message;

    public LocalWeatherAsyncTask(Activity context, String message) {
        this.context = context;
        this.message = message;
        localWeatherService = new LocalWeatherService();
    }

    public LocalWeatherAsyncTask(Activity context) {
        this.context = context;
        localWeatherService = new LocalWeatherService();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = ProgressDialog.show(context, "Local Weather Info", "Fetching DATA");

    }

    @Override
    protected DataElement doInBackground(String... params) {

        String response = localWeatherService.getLocalWeather(params[0]);

        LocalWeatherParser parser = new LocalWeatherParser(context, message);
        DataElement dataElement = parser.parseJsonResponse(response);

        return dataElement;
    }


    @Override
    protected void onPostExecute(DataElement dataElement) {
        super.onPostExecute(dataElement);

        dialog.dismiss();

        if (message != null) {
            if (message.equals("default")) {
                return;
            }
        }

        LayoutInflater li = LayoutInflater.from(context);
        detail = li.inflate(R.layout.activity_weather_detail, null);

        //Query
        TextView place = (TextView) detail.findViewById(R.id.tvPlace);
        place.setText(dataElement.getRequestElement().getQuery());

        //Weather Description
        TextView weatherDesc = (TextView) detail.findViewById(R.id.tvWeatherDesc);
        weatherDesc.setText(dataElement.getCurrentConditionElement().getWeatherDescription());

        //TEMP C
        TextView tempC = (TextView) detail.findViewById(R.id.tvTemp);
        Character deg = '\u00B0';
        tempC.setText(dataElement.getCurrentConditionElement().getTempC() + deg);

        //Humidity
        TextView humidity = (TextView) detail.findViewById(R.id.tvHumidity);
        humidity.setText(dataElement.getCurrentConditionElement().getHumidity() + "%");

        //Wind
        TextView wind = (TextView) detail.findViewById(R.id.tvWind);
        wind.setText(dataElement.getCurrentConditionElement().getWindDirection16Point() + " at " +
                dataElement.getCurrentConditionElement().getWindSpeedMiles() + " mph");

        //Cloud cover
        TextView cc = (TextView) detail.findViewById(R.id.tvCloudCover);
        cc.setText(dataElement.getCurrentConditionElement().getCloudCover() + "%");

        //Precip
        TextView precip = (TextView) detail.findViewById(R.id.tvPrecip);
        precip.setText(dataElement.getCurrentConditionElement().getPrecipitationMM() + "%");


        //iconURL
        ImageView imgView = (ImageView) detail.findViewById(R.id.imgCurrent);
        new DownloadImageTask(imgView).execute(dataElement.getCurrentConditionElement().getWeatherIconUrl());


        //FORECAST
        LinearLayout forecast = (LinearLayout) detail.findViewById(R.id.linForecast);


        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        SimpleDateFormat main = new SimpleDateFormat("yyyy-MM-dd");

        for (WeatherElement we : dataElement.getWeatherElement()) {

            try {
                LinearLayout loadDay = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.day, null);
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


        context.setContentView(detail);


    }


}
