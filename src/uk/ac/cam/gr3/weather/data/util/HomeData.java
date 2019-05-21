package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//This provides data for the home screen
public class HomeData {

    //Use the getter methods below to access the data
    // Main data
    private final int currentTemperature;
    private final String weatherCondition;
    private final String weatherIcon;

    // 3-hourly breakdown data
    private final List<Hour> timeline;

    // Widgets data
    private final int visibility;
    private final int windSpeed;
    private final int cloudCoverage;
    private final String sunrise;
    private final String sunset;

    private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Paris");

    //location needs to be "upper" or "base"
    HomeData(String location, JSONObject sunData, JSONArray forecastData) {
        int numFutureHours = 7;

        //This is the 3-hourly weather timeline
        List<Hour> timeline = new ArrayList<>();
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


        currentTemperature = locationSpecificData.getInt("temp_c");
        weatherCondition = locationSpecificData.getString("wx_desc");
        weatherIcon = locationSpecificData.getString("wx_icon");

        this.timeline = Collections.unmodifiableList(timeline);

        visibility = nonLocationSpecificData.getInt("vis_km");
        windSpeed = locationSpecificData.getInt("windspd_kmh");

        cloudCoverage = (location.equals("upper") ? nonLocationSpecificData.getInt("highcloud_pct") : nonLocationSpecificData.getInt("lowcloud_pct") );

        sunrise = sunData.getString("sunrise_time");
        sunset = sunData.getString("sunset_time");
    }

    //getters
    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public List<Hour> getTimeline() {
        return timeline;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getCloudCoverage() {
        return cloudCoverage;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
