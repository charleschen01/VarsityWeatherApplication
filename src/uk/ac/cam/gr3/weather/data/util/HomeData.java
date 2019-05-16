package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

//This provides data for the home screen
public class HomeData {
    //Use the getter methods below to access the data
    private int currentTemperature;
    private String weatherCondition;
    private String weatherIcon;
    private ArrayList<Hour> timeline;
    private int visibility;
    private int windSpeed;
    private String sunrise;
    private String sunset;


    //These are some extra states that could be used for widgets or deleted
    private String windDirection;
    private int freshSnow;
    private int cloudCoverage;
    private int humidity;

    private int numFutureHours;

    //location needs to be "upper" or "base"
    public HomeData (String location, JSONObject sunData, JSONObject currentLocationforecastData, JSONObject forecastData, JSONArray allForecastData) {
        numFutureHours = 7;
        sunrise = sunData.getString("sunrise_time");
        sunset = sunData.getString("sunset_time");

        currentTemperature = currentLocationforecastData.getInt("temp_c");
        weatherCondition = currentLocationforecastData.getString("wx_desc");
        weatherIcon = currentLocationforecastData.getString("wx_icon");
        windSpeed = currentLocationforecastData.getInt("windspd_kmh");
        windDirection = currentLocationforecastData.getString("winddir_compass");
        freshSnow = currentLocationforecastData.getInt("freshsnow_cm");

        visibility = forecastData.getInt("vis_km");
        humidity = forecastData.getInt("hum_pct");
        cloudCoverage = (location.equals("upper") ? forecastData.getInt("highcloud_pct") :forecastData.getInt("lowcloud_pct") );


        timeline = new ArrayList<>();
        String hour;
        int temperture;
        String weatherIcon;
        int index = 0;

        while (index < numFutureHours){
            JSONObject forecastTimeframe = allForecastData.getJSONObject(index);
            JSONObject currentLocationforecastTimeframe = forecastTimeframe.getJSONObject(location);
            hour = forecastTimeframe.getString("time");
            temperture = currentLocationforecastTimeframe.getInt("temp_c");
            weatherIcon = currentLocationforecastTimeframe.getString("wx_icon");
            Hour h = new Hour(hour, temperture, weatherIcon);
            timeline.add(h);
            index++;
        }

    }

    //getters
    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public ArrayList<Hour> getTimeline() {
        return timeline;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getVisibility() {
        return visibility;
    }

    public String getSunrise() {
        return sunrise;
    }

    //getters for extra data
    public String getSunset() {
        return sunset;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public int getFreshSnow() {
        return freshSnow;
    }

    public int getCloudCoverage() {
        return cloudCoverage;
    }

    public int getHumidity() {
        return humidity;
    }
}
