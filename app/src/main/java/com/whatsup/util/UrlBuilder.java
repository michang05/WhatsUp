package com.whatsup.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by MichaelAngelo on 10/15/2014.
 */
public class UrlBuilder {


    public String createUrlQuery(String servicePath, String query, int forecastDays, String apiKey) {
        StringBuilder sb = new StringBuilder();

        sb.append(servicePath);
        sb.append("?");
        try {
            sb.append("q=" + URLEncoder.encode(query, "UTF-8")).append("&");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("format=json").append("&");
        sb.append("num_of_days=" + forecastDays).append("&");
        sb.append("key=" + apiKey);

        return sb.toString();
    }
}
