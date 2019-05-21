package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONObject;

public class SnowData {
    //Use the getter methods below to access the data
    private final String snowConditions;
    private final String lastSnowed;
    private final int percentageOpenRuns;
    private final double snowFallTop;
    private final double snowFallBottom;

    SnowData(JSONObject data) {
        //updates the attributes with the data from the Snow report
        snowConditions = data.getString("conditions");
        lastSnowed = data.getString("lastsnow");
        percentageOpenRuns = data.getInt("pctopen");
        snowFallTop = data.getDouble("uppersnow_cm");
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

