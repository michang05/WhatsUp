package com.whatsup.parser;

import android.content.Context;
import android.util.Log;

import com.whatsup.db.CurrentWeatherDb;
import com.whatsup.db.ForecastDb;
import com.whatsup.db.RequestDb;
import com.whatsup.model.CurrentConditionElement;
import com.whatsup.model.DataElement;
import com.whatsup.model.RequestElement;
import com.whatsup.model.WeatherElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public class LocalWeatherParser extends BaseParser {

    private DataElement dataElement;
    private Context ctx;
    private String message;


    public LocalWeatherParser(Context ctx, String message) {
        this.ctx = ctx;
        this.message = message;
    }

    @Override
    public DataElement parseJsonResponse(String response) {

        try {
            JSONObject json = new JSONObject(response);

            JSONObject data = json.getJSONObject("data");

            //CURRENT ELEMENT
            JSONArray cc = data.getJSONArray(CURRENT_CONDITION);

            String tempC = cc.getJSONObject(0).optString(TEMP_C);
            String humidity = cc.getJSONObject(0).optString(HUMIDITY);
            String cloudCover = cc.getJSONObject(0).optString(CLOUDCOVER);
            String windDir16Point = cc.getJSONObject(0).optString(WIND_DIR_16POINT);
            String windSpeedMiles = cc.getJSONObject(0).optString(WIND_SPEED_MILES);
            String windSpeedKmph = cc.getJSONObject(0).optString(WIND_SPEED_KMPH);
            String precip = cc.getJSONObject(0).optString(PRECIP_MM);
            String pressure = cc.getJSONObject(0).optString(PRESSURE);
            String visibility = cc.getJSONObject(0).optString(VISIBILITY);
            String weatherCode = cc.getJSONObject(0).optString(WEATHER_CODE);
            JSONObject desc = cc.getJSONObject(0);
            String weatherDesc = desc.getJSONArray(WEATHER_DESCRIPTION).getJSONObject(0).optString(VALUE);


            JSONObject iconUrl = cc.getJSONObject(0);
            String weatherIconUrl = iconUrl.getJSONArray(WEATHER_ICON_URL).getJSONObject(0).optString(VALUE);

            CurrentConditionElement ce = new CurrentConditionElement();
            ce.setWeatherDescription(weatherDesc);
            ce.setTempC(tempC);
            ce.setHumidity(humidity);
            ce.setVisibility(visibility);
            ce.setCloudCover(cloudCover);
            ce.setPressure(pressure);
            ce.setWindDirection16Point(windDir16Point);
            ce.setWindSpeedMiles(windSpeedMiles);
            ce.setWindSpeedKmph(windSpeedKmph);
            ce.setPrecipitationMM(precip);
            ce.setWeatherIconUrl(weatherIconUrl);
            ce.setWeatherCode(weatherCode);


            //REQUEST ELEMENT
            JSONArray request = data.getJSONArray(REQUEST);
            String query = request.getJSONObject(0).optString(QUERY);
            String type = request.getJSONObject(0).optString(TYPE);
            ce.setQuery(query);
            //---------


            RequestElement re = new RequestElement();
            re.setQuery(query);
            re.setType(type);

            //WEATHER ELEMENT

            JSONArray weather = data.getJSONArray(WEATHER);
            ArrayList<WeatherElement> forecast = new ArrayList<WeatherElement>();

            for (int i = 0; i < weather.length(); i++) {

                String date = weather.getJSONObject(i).optString(DATE);
                String tempMinC = weather.getJSONObject(i).optString(TEMP_MIN_C);
                String tempMaxC = weather.getJSONObject(i).optString(TEMP_MAX_C);
                JSONObject weatherIconUrlImage = weather.getJSONObject(i);
                String imgUrl = weatherIconUrlImage.getJSONArray(WEATHER_ICON_URL).getJSONObject(0).optString(VALUE);
                JSONObject weatherDescImage = weather.getJSONObject(i);
                String weatherDescImg = weatherDescImage.getJSONArray(WEATHER_DESCRIPTION).getJSONObject(0).optString(VALUE);
                String windDirection = weather.getJSONObject(i).optString(WIND_DIRECTION);
                String precipMM = weather.getJSONObject(i).optString(PRECIP_MM);
                String wCode = weather.getJSONObject(i).optString(WEATHER_CODE);
                String wind16 = weather.getJSONObject(i).optString(WIND_DIR_16POINT);

                WeatherElement d1 = new WeatherElement();
                d1.setWeatherIconUrl(imgUrl);
                d1.setDate(date);
                d1.setTempMinC(tempMinC);
                d1.setTempMaxC(tempMaxC);
                d1.setWeatherDescription(weatherDescImg);
                d1.setQueryId(query);
                d1.setPrecipitationMM(precipMM);
                d1.setWindDirection(windDirection);
                d1.setWeatherCode(wCode);
                d1.setWindDirection16Point(wind16);
                forecast.add(d1);
            }

            CurrentWeatherDb currentWeatherDbdb = new CurrentWeatherDb(ctx);
            RequestDb requestDb = new RequestDb(ctx);
            ForecastDb forecastDbdb = new ForecastDb(ctx);


            if (message != null) {
                if (message.equals("refresh") || currentWeatherDbdb.isCurrentWeatherExist(query)) {
                    currentWeatherDbdb.delete(query);
                }
            }

            currentWeatherDbdb.insert(ce);
            requestDb.insert(re);

            for (WeatherElement weatherF : forecast) {
                forecastDbdb.insert(weatherF);
            }


            dataElement = new DataElement();
            dataElement.setCurrentConditionElement(ce);
            dataElement.setRequestElement(re);
            dataElement.setWeatherElement(forecast);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataElement;
    }


}
