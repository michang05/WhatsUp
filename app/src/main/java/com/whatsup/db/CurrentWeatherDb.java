package com.whatsup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.whatsup.model.CurrentConditionElement;
import com.whatsup.model.RequestElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MichaelAngelo on 10/18/2014.
 */


public class CurrentWeatherDb extends DatabaseOpenHelper {


    private SQLiteDatabase db;

    public CurrentWeatherDb(Context context) {
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


    public void insert(CurrentConditionElement currentWeather) {
        open();


        ContentValues values = new ContentValues();
        values.put(CLOUD_COVER, currentWeather.getCloudCover());
        values.put(HUMIDITY, currentWeather.getHumidity());
        values.put(PRECIP_MM, currentWeather.getPrecipitationMM());
        values.put(TEMP_C, currentWeather.getTempC());
        values.put(QUERY, currentWeather.getQuery());
        values.put(VISIBILITY, currentWeather.getVisibility());
        values.put(PRESSURE, currentWeather.getPressure());
        values.put(WEATHER_CODE, currentWeather.getWeatherCode());
        values.put(WEATHER_DESCRIPTION, currentWeather.getWeatherDescription());
        values.put(WEATHER_ICON_URL, currentWeather.getWeatherIconUrl());
        values.put(WIND_DIRECTION_16POINT, currentWeather.getWindDirection16Point());
        values.put(WIND_DIRECTION_KMPH, currentWeather.getWindSpeedKmph());
        values.put(WIND_DIRECTION_MILES, currentWeather.getWindSpeedMiles());


        db.insert(CURRENT_WEATHER_TABLE, null, values);

        close();
    }

    public boolean isCurrentWeatherExist(String query) {
        open();

        boolean isCurrentWeatherExist = false;
        Cursor cursor = db.query(CURRENT_WEATHER_TABLE, null, QUERY + "=?", new String[]{
                query
        }, null, null, null);
        CurrentConditionElement currentWeather = null;
        RequestElement requestElement = null;
        if (cursor != null) {
            cursor.moveToFirst();

            currentWeather = new CurrentConditionElement();

            currentWeather.setCloudCover(cursor.getString(cursor.getColumnIndexOrThrow(CLOUD_COVER)));
            currentWeather.setHumidity(cursor.getString(cursor.getColumnIndexOrThrow(HUMIDITY)));
            currentWeather.setPrecipitationMM(cursor.getString(cursor.getColumnIndexOrThrow(PRECIP_MM)));
            currentWeather.setTempC(cursor.getString(cursor.getColumnIndexOrThrow(TEMP_C)));
            currentWeather.setQuery(cursor.getString(cursor.getColumnIndexOrThrow(QUERY)));
            currentWeather.setPressure(cursor.getString(cursor.getColumnIndexOrThrow(PRESSURE)));
            currentWeather.setVisibility(cursor.getString(cursor.getColumnIndexOrThrow(VISIBILITY)));
            currentWeather.setWeatherCode(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_CODE)));
            currentWeather.setWeatherDescription(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_DESCRIPTION)));
            currentWeather.setWeatherIconUrl(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_ICON_URL)));
            currentWeather.setWindDirection16Point(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_16POINT)));
            currentWeather.setWindSpeedMiles(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_MILES)));
            currentWeather.setWindSpeedKmph(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_KMPH)));
        }

        return isCurrentWeatherExist;
    }

    public CurrentConditionElement getCurrentWeather(String query) {
        open();

        Cursor cursor = db.query(CURRENT_WEATHER_TABLE, null, QUERY + "=?", new String[]{
                query
        }, null, null, null);
        CurrentConditionElement currentWeather = null;
        if (cursor != null) {
            cursor.moveToFirst();

            currentWeather = new CurrentConditionElement();

            currentWeather.setCloudCover(cursor.getString(cursor.getColumnIndexOrThrow(CLOUD_COVER)));
            currentWeather.setHumidity(cursor.getString(cursor.getColumnIndexOrThrow(HUMIDITY)));
            currentWeather.setPrecipitationMM(cursor.getString(cursor.getColumnIndexOrThrow(PRECIP_MM)));
            currentWeather.setTempC(cursor.getString(cursor.getColumnIndexOrThrow(TEMP_C)));
            currentWeather.setVisibility(cursor.getString(cursor.getColumnIndexOrThrow(VISIBILITY)));
            currentWeather.setWeatherCode(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_CODE)));
            currentWeather.setQuery(cursor.getString(cursor.getColumnIndexOrThrow(QUERY)));
            currentWeather.setWeatherDescription(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_DESCRIPTION)));
            currentWeather.setWeatherIconUrl(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_ICON_URL)));
            currentWeather.setWindDirection16Point(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_16POINT)));
            currentWeather.setWindSpeedMiles(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_MILES)));
            currentWeather.setWindSpeedKmph(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_KMPH)));
        }

        return currentWeather;
    }


    public List<CurrentConditionElement> getAllCurrentWeather() {

        open();
        Cursor cursor = db.query(CURRENT_WEATHER_TABLE, null, null, null, null, null, null);

        ArrayList<CurrentConditionElement> listCurrentWeather = new ArrayList<CurrentConditionElement>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            CurrentConditionElement currentWeather = new CurrentConditionElement();

            currentWeather.setCloudCover(cursor.getString(cursor.getColumnIndexOrThrow(CLOUD_COVER)));
            currentWeather.setHumidity(cursor.getString(cursor.getColumnIndexOrThrow(HUMIDITY)));
            currentWeather.setPrecipitationMM(cursor.getString(cursor.getColumnIndexOrThrow(PRECIP_MM)));
            currentWeather.setTempC(cursor.getString(cursor.getColumnIndexOrThrow(TEMP_C)));
            currentWeather.setQuery(cursor.getString(cursor.getColumnIndexOrThrow(QUERY)));
            currentWeather.setVisibility(cursor.getString(cursor.getColumnIndexOrThrow(VISIBILITY)));
            currentWeather.setWeatherCode(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_CODE)));
            currentWeather.setWeatherDescription(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_DESCRIPTION)));
            currentWeather.setWeatherIconUrl(cursor.getString(cursor.getColumnIndexOrThrow(WEATHER_ICON_URL)));
            currentWeather.setWindDirection16Point(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_16POINT)));
            currentWeather.setWindSpeedMiles(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_MILES)));
            currentWeather.setWindSpeedKmph(cursor.getString(cursor.getColumnIndexOrThrow(WIND_DIRECTION_KMPH)));


            listCurrentWeather.add(currentWeather);

            cursor.moveToNext();
        }

        cursor.close();
        close();

        return listCurrentWeather;
    }


    public void delete() {
        open();
        db.delete(CURRENT_WEATHER_TABLE, "1", null);
        close();
    }

    /**
     * Deletes current and affects the forecastDB , just a quick delete can be change.
     *
     * @param query
     */
    public void delete(String query) {
        open();
        db.execSQL("DELETE FROM " + CURRENT_WEATHER_TABLE + " WHERE query = '" + query + "'");
        db.execSQL("DELETE FROM " + REQUEST_TABLE + " WHERE query = '" + query + "'");
        db.execSQL("DELETE FROM " + FORECAST_TABLE + " WHERE query_name = '" + query + "'");
        close();
    }
}
