package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

//This provides data for the weekly report
public class WeeklyData {
    //Use the getter methods below to access the data
    ArrayList<Day> week;

    public WeeklyData () {
        week = new ArrayList<>();
        refresh();
    }

    //refresh weekly data
    public void refresh(){
        JSONObject data = new JSONObject();
        try {
            data = URLConnectionReader.getJSON("http://api.weatherunlocked.com/api/forecast/fr.73440?app_id=34ef604f&app_key=9e15b25a985bb34ddc8db6f973324353");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray weekForecast =  data.getJSONArray("Days");

        week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            JSONObject dayForecast = weekForecast.getJSONObject(i);
            String date = dayForecast.getString("date");
            int lowestTempersture = dayForecast.getInt("temp_max_c");
            int highestTempersture = dayForecast.getInt("temp_min_c");
            String weatherIcon =  "WeatherIcons/" + dayForecast.getJSONArray("Timeframes").getJSONObject(0).getString("wx_icon");
            Day day = new Day(date, lowestTempersture, highestTempersture, weatherIcon);
            week.add(day);
        }
    }

    //getters
    public ArrayList<Day> getWeek() {
        return week;
    }
}
