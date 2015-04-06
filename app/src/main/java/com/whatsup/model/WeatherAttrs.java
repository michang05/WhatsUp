package com.whatsup.model;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public abstract class WeatherAttrs {

    private String precipitationMM;
    private String weatherCode;
    private String weatherDescription;
    private String weatherIconUrl;
    private String windDirection16Point;
    private String windDirectionDegree;
    private String windSpeedKmph;
    private String windSpeedMiles;

    public String getPrecipitationMM() {
        return precipitationMM;
    }

    public void setPrecipitationMM(String precipitationMM) {
        this.precipitationMM = precipitationMM;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getWindDirection16Point() {
        return windDirection16Point;
    }

    public void setWindDirection16Point(String windDirection16Point) {
        this.windDirection16Point = windDirection16Point;
    }

    public String getWindDirectionDegree() {
        return windDirectionDegree;
    }

    public void setWindDirectionDegree(String windDirectionDegree) {
        this.windDirectionDegree = windDirectionDegree;
    }

    public String getWindSpeedKmph() {
        return windSpeedKmph;
    }

    public void setWindSpeedKmph(String windSpeedKmph) {
        this.windSpeedKmph = windSpeedKmph;
    }

    public String getWindSpeedMiles() {
        return windSpeedMiles;
    }

    public void setWindSpeedMiles(String windSpeedMiles) {
        this.windSpeedMiles = windSpeedMiles;
    }
}
