package com.whatsup.db;

import com.whatsup.model.DataElement;

/**
 * Created by MichaelAngelo on 10/18/2014.
 */
public class DatabaseManager {

    private CurrentWeatherDb currentWeatherDb;
    private ForecastDb forecastDb;
    private DataElement dataElement;

    public DatabaseManager(DataElement dataElement) {
        this.dataElement = dataElement;
    }

}
