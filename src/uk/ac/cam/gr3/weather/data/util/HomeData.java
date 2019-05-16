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
    public HomeData (String location, JSONObject sunData, JSONObject currentLocationForecastData, JSONObject forecastData, JSONArray allForecastData) {
        numFutureHours = 7;
        sunrise = sunData.getString("sunrise_time");
        sunset = sunData.getString("sunset_time");

        currentTemperature = currentLocationForecastData.getInt("temp_c");
        weatherCondition = currentLocationForecastData.getString("wx_desc");
        weatherIcon = currentLocationForecastData.getString("wx_icon");
        windSpeed = currentLocationForecastData.getInt("windspd_kmh");
        windDirection = currentLocationForecastData.getString("winddir_compass");
        freshSnow = currentLocationForecastData.getInt("freshsnow_cm");

        visibility = forecastData.getInt("vis_km");
        humidity = forecastData.getInt("hum_pct");
        cloudCoverage = (location.equals("upper") ? forecastData.getInt("highcloud_pct") :forecastData.getInt("lowcloud_pct") );


        timeline = new ArrayList<>();
        String hour;
        int temperature;
        String weatherIcon;
        int index = 0;

        while (index < numFutureHours){
            JSONObject forecastTimeFrame = allForecastData.getJSONObject(index);
            JSONObject currentLocationForecastTimeFrame = forecastTimeFrame.getJSONObject(location);
            hour = forecastTimeFrame.getString("time");
            temperature = currentLocationForecastTimeFrame.getInt("temp_c");
            weatherIcon = currentLocationForecastTimeFrame.getString("wx_icon");
            Hour h = new Hour(hour, temperature, weatherIcon);
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
