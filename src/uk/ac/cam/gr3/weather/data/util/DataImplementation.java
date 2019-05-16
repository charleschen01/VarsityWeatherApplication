package uk.ac.cam.gr3.weather.data.util;
import org.json.JSONObject;
import uk.ac.cam.gr3.weather.data.WeatherService;

import java.io.IOException;

public class DataImplementation implements WeatherService {

    private JSONObject data;
    private JSONObject generalData;
    private JSONObject snowReport;

    private HomeData peakData;
    private HomeData baseData;
    private SnowData snowData;
    private WeeklyData weeklyData;

    public DataImplementation(){
        JSONObject data = new JSONObject();
        JSONObject generalData = new JSONObject();
        JSONObject snowReport = new JSONObject();
        refresh();
    }

    @Override
    public void refresh() {
        setData();
        HomeData peakData = new HomeData("upper", generalData.getJSONArray("Days").getJSONObject(0), data.getJSONArray("forecast").getJSONObject(0).getJSONObject("upper"), data.getJSONArray("forecast").getJSONObject(0), data.getJSONArray("forecast"));
        HomeData baseData = new HomeData("base", generalData.getJSONArray("Days").getJSONObject(0), data.getJSONArray("forecast").getJSONObject(0).getJSONObject("base"), data.getJSONArray("forecast").getJSONObject(0), data.getJSONArray("forecast"));
        SnowData snowData = new SnowData(snowReport);
        WeeklyData weeklyData = new WeeklyData(data.getJSONArray("Days"));
    }

    @Override
    public HomeData getPeakData() {
        return peakData;
    }

    @Override
    public HomeData getBaseData() {
        return baseData;
    }

    @Override
    public SnowData getSnowData() {
        return snowData;
    }

    @Override
    public WeeklyData getWeeklyData() {
        return weeklyData;
    }

    public void setData(){
        try {
            data = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/resortforecast/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
            generalData = URLConnectionReader.getJSON("http://api.weatherunlocked.com/api/forecast/fr.73440?app_id=34ef604f&app_key=9e15b25a985bb34ddc8db6f973324353");
            snowReport = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/snowreport/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
