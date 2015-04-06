package com.whatsup.service;

import android.util.Base64;
import android.util.Log;

import com.whatsup.parser.LocalWeatherParser;
import com.whatsup.util.DataHelper;
import com.whatsup.util.UrlBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.regex.Matcher;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public class LocalWeatherService {

    public static final String BASE_URL = "http://api.worldweatheronline.com";
    public static final String LOCAL_WEATHER = BASE_URL + "/free/v1/weather.ashx";
    private static final String API_KEY = "890cc1cd7a47bb46963f64fc8249125af59c314d";
    private static final int FORECAST_DAYS = 5;
    private LocalWeatherParser localWeatherParser;

    private HttpURLConnection con;
    private UrlBuilder urlBuilder;
    private String result;

    public String getLocalWeather(String query) {

        try {
            urlBuilder = new UrlBuilder();
            URL url = new URL(urlBuilder.createUrlQuery(LOCAL_WEATHER, query, FORECAST_DAYS, API_KEY));
            Log.i("--------------------getLocalWeather---------------------", "requestURL: " + url.toExternalForm());
            con = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(con.getInputStream());
            result = DataHelper.convertStreamToString(in);


            Log.i("--------------------getLocalWeather---------------------", result);

            in.close();
        } catch (MalformedURLException e) {

            e.printStackTrace();


        } catch (IOException e) {

            e.printStackTrace();


        } finally {
            con.disconnect();
        }

        return result;
    }

}
