package com.whatsup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.whatsup.model.CurrentConditionElement;
import com.whatsup.model.WeatherElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MichaelAngelo on 10/18/2014.
 */
public class ForecastDb extends DatabaseOpenHelper {


    private SQLiteDatabase db;

    public ForecastDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        db.close();
    }

    public void open() throws SQLiteException {
        try {
            db = getWritableDatabase();
        } catch (SQLiteException ex) {
            db = getReadableDatabase();
        }
    }

    public void delete() {
        open();
        db.delete(FORECAST_TABLE, "1", null);
        close();
    }

    public void insert(WeatherElement weatherElement) {
        open();


        ContentValues values = new ContentValues();
        values.put(QUERY_NAME, weatherElement.getQueryId());
        values.put(DATE, weatherElement.getDate());
        values.put(PRECIP_MM, weatherElement.getPrecipitationMM());
        values.put(TEMP_MAX_C, weatherElement.getTempMaxC());
        values.put(TEMP_MIN_C, weatherElement.getTempMinC());
        values.put(WEATHER_CODE, weatherElement.getWeatherCode());
        values.put(WEATHER_DESCRIPTION, weatherElement.getWeatherDescription());
        values.put(WEATHER_ICON_URL, weatherElement.getWeatherIconUrl());
        values.put(WIND_DIRECTION_16POINT, weatherElement.getWindDirection16Point());
        values.put(WIND_DIRECTION, weatherElement.getWindDirection());


        db.insert(FORECAST_TABLE, null, values);

        close();
    }


    public List<WeatherElement> getAllForecast() {

        open();
        Cursor cursor = db.query(FORECAST_TABLE, null, null, null, null, null, null);

        ArrayList<WeatherElement> listForecast = new ArrayList<WeatherElement>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            WeatherElement forecast = new WeatherElement();

            forecast.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
            forecast.setPrecipitationMM(cursor.getString(cursor.getColumnIndexOrThrow(PRECIP_MM)));

            forecast.setTempMaxC(cursor.getString(cursor.getColumnIndexOrThrow(TEMP_MAX_C)));
            forecast.setTempMinC(cursor.getString(cursor.getColumnIndexOrThrow(TEMP_MIN_C)));

            forecast.setWeatherCode(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_CODE)));
            forecast.setWeatherDescription(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_DESCRIPTION)));
            forecast.setWeatherIconUrl(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_ICON_URL)));
            forecast.setWindDirection16Point(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_16POINT)));


            listForecast.add(forecast);

            cursor.moveToNext();
        }

        cursor.close();
        close();

        return listForecast;
    }


    public List<WeatherElement> getAllForecast(String query) {

        open();
        Cursor cursor = db.query(FORECAST_TABLE, null, QUERY_NAME + "=?", new String[]{
                query
        }, null, null, null);

        ArrayList<WeatherElement> listForecast = new ArrayList<WeatherElement>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            WeatherElement forecast = new WeatherElement();

            forecast.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
            forecast.setPrecipitationMM(cursor.getString(cursor.getColumnIndexOrThrow(PRECIP_MM)));

            forecast.setTempMaxC(cursor.getString(cursor.getColumnIndexOrThrow(TEMP_MAX_C)));
            forecast.setTempMinC(cursor.getString(cursor.getColumnIndexOrThrow(TEMP_MIN_C)));
            forecast.setQueryId(query);
            forecast.setWeatherCode(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_CODE)));
            forecast.setWeatherDescription(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_DESCRIPTION)));
            forecast.setWeatherIconUrl(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_ICON_URL)));
            forecast.setWindDirection16Point(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_16POINT)));


            listForecast.add(forecast);

            cursor.moveToNext();
        }

        cursor.close();
        close();

        return listForecast;
    }
}
