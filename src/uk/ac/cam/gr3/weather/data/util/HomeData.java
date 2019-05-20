package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//This provides data for the home screen
public class HomeData {
    //Use the getter methods below to access the data
    private int currentTemperature;
    private String weatherCondition;
    private String weatherIcon;
    private ArrayList<Hour> timeline;
    private int visibility;
    private int windSpeed;
    private int cloudCoverage;
    private String sunrise;


    //extra data -delete if not used as widget
    private String sunset;
    private String windDirection;
    private int freshSnow;
    private int humidity;

    private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Paris");

    //location needs to be "upper" or "base"
    HomeData(String location, JSONObject sunData, JSONArray forecastData) {
        int numFutureHours = 7;

        //This is the 3-hourly weather timeline
        timeline = new ArrayList<>();
        String hour;
        int temperature;
        String weatherIconForecast;

        Hour next = null;
        int currentTimeIndex = -1;

        for (int index = 0; index < numFutureHours; index++){
            JSONObject forecastTimeFrame = forecastData.getJSONObject(index);
            JSONObject currentLocationForecastTimeFrame = forecastTimeFrame.getJSONObject(location);
            hour = forecastTimeFrame.getString("time");
            if(currentTimeIndex==-1) {
                numFutureHours++;
                List<Integer> date = Arrays.stream(forecastTimeFrame.getString("date").split("/")).map(Integer::parseInt).collect(Collectors.toList());
                List<Integer> time = Arrays.stream(hour.split(":")).map(Integer::parseInt).collect(Collectors.toList());
                Instant now = Instant.now(Clock.systemUTC().withZone(TIME_ZONE));
                LocalDateTime forecastHour = LocalDateTime.of(date.get(2), date.get(1), date.get(0), time.get(0), time.get(1));
                Instant forecastHourZoned = forecastHour.atZone(TIME_ZONE).toInstant();
                if(forecastHourZoned.isAfter(now)) {
                    currentTimeIndex = Math.max(index - 1, 0);
                    if(next != null) {
                        timeline.add(next);
                    }
                }
            }
            temperature = currentLocationForecastTimeFrame.getInt("temp_c");
            weatherIconForecast = currentLocationForecastTimeFrame.getString("wx_icon");
            next = new Hour(hour, temperature, weatherIconForecast);
            if(currentTimeIndex>=0) {
                timeline.add(next);
            }
        }

        //data for the current timestamp
        JSONObject locationSpecificData = forecastData.getJSONObject(currentTimeIndex).getJSONObject(location);
        JSONObject nonLocationSpecificData = forecastData.getJSONObject(currentTimeIndex);


        sunrise = sunData.getString("sunrise_time");

        currentTemperature = locationSpecificData.getInt("temp_c");
        weatherCondition = locationSpecificData.getString("wx_desc");
        weatherIcon = locationSpecificData.getString("wx_icon");
        windSpeed = locationSpecificData.getInt("windspd_kmh");

        cloudCoverage = (location.equals("upper") ? nonLocationSpecificData.getInt("highcloud_pct") : nonLocationSpecificData.getInt("lowcloud_pct") );
        visibility = nonLocationSpecificData.getInt("vis_km");

        //extra data -delete if not used as widget
        sunset = sunData.getString("sunset_time");
        humidity = nonLocationSpecificData.getInt("hum_pct");
        freshSnow = locationSpecificData.getInt("freshsnow_cm");
        windDirection = locationSpecificData.getString("winddir_compass");
    }

    //getters
    public int getCloudCoverage() {
        return cloudCoverage;
    }

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

    //setters
    void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    void setCloudCoverage(int cloudCoverage) {
        this.cloudCoverage = cloudCoverage;
    }

    void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    void setTimeline(JSONArray data, String location) {
        int index = 0;
        for (Hour hour : timeline){
            JSONObject timestampData = data.getJSONObject(index);
            hour.setHour(timestampData.getString("time"));
            hour.setTemperature(timestampData.getJSONObject(location).getInt("temp_c"));
            hour.setWeatherIcon(timestampData.getJSONObject(location).getString("wx_icon"));
            index ++;
        }
    }


    //getters and setters for extra data
    public String getSunset() {
        return sunset;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public int getFreshSnow() {
        return freshSnow;
    }

    public int getHumidity() {
        return humidity;
    }

    void setSunset(String sunset) {
        this.sunset = sunset;
    }

    void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    void setFreshSnow(int freshSnow) {
        this.freshSnow = freshSnow;
    }

    void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
