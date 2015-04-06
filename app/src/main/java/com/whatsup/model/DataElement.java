package com.whatsup.model;

import java.util.List;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public class DataElement {

    private CurrentConditionElement currentConditionElement;
    private RequestElement requestElement;
    private List<WeatherElement> weatherElement;

    public CurrentConditionElement getCurrentConditionElement() {
        return currentConditionElement;
    }

    public void setCurrentConditionElement(CurrentConditionElement currentConditionElement) {
        this.currentConditionElement = currentConditionElement;
    }

    public RequestElement getRequestElement() {
        return requestElement;
    }

    public void setRequestElement(RequestElement requestElement) {
        this.requestElement = requestElement;
    }

    public List<WeatherElement> getWeatherElement() {
        return weatherElement;
    }

    public void setWeatherElement(List<WeatherElement> weatherElement) {
        this.weatherElement = weatherElement;
    }
}
