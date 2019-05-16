package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SnowData {
    //Use the getter methods below to access the data
    private String snowConditions;
    private String lastSnowed;
    private int percentageOpenRuns;
    private double snowFallTop;
    private double snowFallBottom;


    public SnowData (JSONObject snowData) {
        //updates the attributes
        snowConditions = snowData.getString("conditions");
        lastSnowed = snowData.getString("lastsnow");
        percentageOpenRuns = snowData.getInt("pctopen");
        snowFallTop = snowData.getDouble("lowersnow_cm");
        snowFallBottom = snowData.getDouble("lowersnow_cm");
    }

    //getters
    public String getSnowConditions() {
        return snowConditions;
    }

    public String getLastSnowed() {
        return lastSnowed;
    }

    public int getPercentageOpenRuns() {
        return percentageOpenRuns;
    }

    public double getSnowFallTop() {
        return snowFallTop;
    }

    public double getSnowFallBottom() {
        return snowFallBottom;
    }

}

