package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
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

    //location needs to be "upper" or "base"
    public HomeData (String location) {
        refresh(location);
    }


    //refresh weather data  -requires location ("upper"/"base")
    public void refresh(String location) {
        JSONObject data = new JSONObject();
        JSONObject sunData = new JSONObject();

        try {
            data = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/resortforecast/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
            sunData = URLConnectionReader.getJSON("http://api.weatherunlocked.com/api/forecast/fr.73440?app_id=34ef604f&app_key=9e15b25a985bb34ddc8db6f973324353");
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONArray allForecastData =  data.getJSONArray("forecast");
        JSONObject forecastData = allForecastData.getJSONObject(0);
        JSONObject currentLocationforecastData = forecastData.getJSONObject(location);

        JSONObject sunForecast =  sunData.getJSONArray("Days").getJSONObject(0);
        sunrise = sunForecast.getString("sunrise_time");
        sunset = sunForecast.getString("sunset_time");

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
        int index = 5;

        while (index < 13){
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

    public String getWindDirection() {
        return windDirection;
    }

    public int getFreshSnow() {
        return freshSnow;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getCloudCoverage() {
        return cloudCoverage;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

}
