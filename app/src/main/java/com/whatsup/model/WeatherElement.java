package com.whatsup.model;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public class WeatherElement extends WeatherAttrs {

    private String date;
    private String tempMaxC;
    private String tempMaxF;
    private String tempMinC;
    private String tempMinF;
    private String windDirection;

    private String queryId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTempMaxC() {
        return tempMaxC;
    }

    public void setTempMaxC(String tempMaxC) {
        this.tempMaxC = tempMaxC;
    }

    public String getTempMaxF() {
        return tempMaxF;
    }

    public void setTempMaxF(String tempMaxF) {
        this.tempMaxF = tempMaxF;
    }

    public String getTempMinC() {
        return tempMinC;
    }

    public void setTempMinC(String tempMinC) {
        this.tempMinC = tempMinC;
    }

    public String getTempMinF() {
        return tempMinF;
    }

    public void setTempMinF(String tempMinF) {
        this.tempMinF = tempMinF;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }
}
