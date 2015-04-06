package com.whatsup.parser;

import com.whatsup.model.DataElement;

import java.util.List;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public abstract class BaseParser {

    //Common to weather and current_condition elements
    protected static final String PRECIP_MM = "precipMM";
    protected static final String WEATHER_CODE = "weatherCode";
    protected static final String WEATHER_DESCRIPTION = "weatherDesc";
    protected static final String WEATHER_ICON_URL = "weatherIconUrl";
    protected static final String WIND_DIR_16POINT = "winddir16Point";
    protected static final String WIND_SPEED_KMPH = "windspeedKmph";
    protected static final String WIND_SPEED_MILES = "windspeedMiles";

    //exclusive to current_condition element
    protected static final String CURRENT_CONDITION = "current_condition";
    protected static final String CLOUDCOVER = "cloudcover";
    protected static final String HUMIDITY = "humidity";
    protected static final String OBSERVATION_TIME = "observation_time";
    protected static final String TEMP_C = "temp_C";
    protected static final String TEMP_F = "temp_F";
    protected static final String VISIBILITY = "visibility";
    protected static final String PRESSURE = "presssure";

    //exclusive to weather element
    protected static final String WEATHER = "weather";
    protected static final String DATE = "date";
    protected static final String TEMP_MAX_C = "tempMaxC";
    protected static final String TEMP_MAX_F = "tempMaxF";
    protected static final String TEMP_MIN_C = "tempMinC";
    protected static final String TEMP_MIN_F = "tempMinF";
    protected static final String WIND_DIRECTION = "winddirection";


    //
    protected static final String REQUEST = "request";
    protected static final String VALUE = "value";

    //request element
    protected static final String QUERY = "query";
    protected static final String TYPE = "type";


    public abstract DataElement parseJsonResponse(String response);
}
