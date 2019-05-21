package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//This provides data for the weekly report
public class WeeklyData {
    //Use the getter methods below to access the data
    private final List<Day> week;

    WeeklyData(JSONArray weekForecast) {
        int numFutureDays = 7;
        List<Day> week = new ArrayList<>();

        //Goes through each day of the week and creates a day with all the needed data
        for (int i = 0; i < numFutureDays; i++) {
            JSONObject dayForecast = weekForecast.getJSONObject(i);
            String date = dayForecast.getString("date");
            int lowestTemperature = dayForecast.getInt("temp_min_c");
            int highestTemperature = dayForecast.getInt("temp_max_c");

            //Gets the midday temperature of the day -(except for the last day due to API data limitations)
            String weatherIcon;
            try {
                weatherIcon = dayForecast.getJSONArray("Timeframes").getJSONObject(4).getString("wx_icon");
            } catch (Exception e){
                weatherIcon = dayForecast.getJSONArray("Timeframes").getJSONObject(0).getString("wx_icon");
            }

            Day day = new Day(date, lowestTemperature, highestTemperature, weatherIcon);
            week.add(day);
        }

        this.week = Collections.unmodifiableList(week);
    }

    //getters
    public List<Day> getWeek() {
        return week;
    }
}

