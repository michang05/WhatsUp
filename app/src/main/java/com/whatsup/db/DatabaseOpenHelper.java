package com.whatsup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MichaelAngelo on 10/18/2014.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "local_weather.db";
    protected static final String CURRENT_WEATHER_TABLE = "current_weather";
    protected static final String REQUEST_TABLE = "request";
    protected static final String FORECAST_TABLE = "forecast";

    //COLUMNS
    protected static final String ID = "_ID";
    protected static final String DATE = "DATE";
    protected static final String TEMP_MIN_C = "TEMP_MIN_C";
    protected static final String TEMP_MAX_C = "TEMP_MAX_C";
    protected static final String WEATHER_CODE = "WEATHER_CODE";
    protected static final String WEATHER_DESCRIPTION = "WEATHER_DESCRIPTION";
    protected static final String WEATHER_ICON_URL = "WEATHER_ICON_URL";
    protected static final String WIND_DIRECTION_DEGREE = "WIND_DIRECTION_DEGREE";
    protected static final String WIND_DIRECTION_16POINT = "WIND_DIRECTION_16POINT";
    protected static final String WIND_DIRECTION_MILES = "WIND_DIRECTION_MILES";
    protected static final String WIND_DIRECTION_KMPH = "WIND_DIRECTION_KMPH";
    protected static final String WIND_DIRECTION = "WIND_DIRECTION";

    //Foreign Key
    protected static final String QUERY_NAME = "QUERY_NAME";

    protected static final String CLOUD_COVER = "CLOUD_COVER";
    protected static final String HUMIDITY = "HUMIDITY";
    protected static final String PRECIP_MM = "PRECIP_MM";
    private static final String FORECAST_CREATE = "create table " + FORECAST_TABLE
            + " (" + ID + " integer primary key autoincrement, "
            + QUERY_NAME + " text not null, "
            + DATE + " text not null, "
            + WIND_DIRECTION + " text not null, "
            + PRECIP_MM + " text not null, "
            + TEMP_MIN_C + " text not null, "
            + TEMP_MAX_C + " text not null, "
            + WEATHER_CODE + " text not null, "
            + WEATHER_DESCRIPTION + " text not null, "
            + WEATHER_ICON_URL + " text not null, "
            + WIND_DIRECTION_16POINT + " text not null);";
    protected static final String TEMP_C = "TEMP_C";
    protected static final String PRESSURE = "PRESSURE";
    protected static final String VISIBILITY = "VISIBILITY";
    protected static final String QUERY = "QUERY";
    private static final String CURRENT_WEATHER_CREATE = "create table " + CURRENT_WEATHER_TABLE
            + " (" + ID + " integer primary key autoincrement, "
            + QUERY + " text not null, "
            + CLOUD_COVER + " text not null, "
            + HUMIDITY + " text not null, "
            + PRECIP_MM + " text not null, "
            + PRESSURE + " text not null, "
            + TEMP_C + " text not null, "
            + VISIBILITY + " text not null, "
            + WEATHER_CODE + " text not null, "
            + WEATHER_DESCRIPTION + " text not null, "
            + WEATHER_ICON_URL + " text not null, "
            + WIND_DIRECTION_16POINT + " text not null, "
            + WIND_DIRECTION_MILES + " text not null, "
            + WIND_DIRECTION_KMPH + " text not null);";
    protected static final String TYPE = "TYPE";
    private static final String REQUEST_CREATE = "create table " + REQUEST_TABLE
            + " (" + ID + " integer primary key autoincrement, "
            + QUERY + " text not null, "
            + TYPE + " text not null);";

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CURRENT_WEATHER_CREATE);
        db.execSQL(FORECAST_CREATE);
        db.execSQL(REQUEST_CREATE);

        Log.i("DB HELPER", "Database has been created SUCCESSFULLY");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CURRENT_WEATHER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FORECAST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REQUEST_TABLE);
        onCreate(db);
    }

}
