package com.whatsup.service;

import java.util.Locale;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public interface WebServicePath {

    public static final String BASE_URL = "http://api.worldweatheronline.com";
    public static final String LOCAL_WEATHER = BASE_URL + "/free/v1/weather.ashx";

    /**
     * Load from Database
     */
    public void loadFromDb();

    /**
     * executes the POST request
     *
     * @return boolean
     */
    public boolean executeRequest();
}
