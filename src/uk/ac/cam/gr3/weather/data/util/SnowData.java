package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONObject;

import java.io.IOException;

public class SnowData {
    //Use the getter methods below to access the data
    private String snowConditions;
    private String lastSnowed;
    private int percentageOpenRuns;
    private double snowFallTop;
    private double snowFallBottom;


    public SnowData () {
        refresh();
    }

    //refresh snow data
    public void refresh() {
        //gets the Json object from the URL
        JSONObject data = new JSONObject();
        try {
            data = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/snowreport/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
        } catch (IOException  e) {
            e.printStackTrace();
        }

        //updates the attributes
        snowConditions = data.getString("conditions");
        lastSnowed = data.getString("lastsnow");
        percentageOpenRuns = data.getInt("pctopen");
        snowFallTop = data.getDouble("lowersnow_cm");
        snowFallBottom = data.getDouble("lowersnow_cm");
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

