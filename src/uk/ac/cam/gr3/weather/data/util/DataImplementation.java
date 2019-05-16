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
        data = new JSONObject();
        generalData = new JSONObject();
        snowReport = new JSONObject();
        try {
            data = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/resortforecast/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
            generalData = URLConnectionReader.getJSON("http://api.weatherunlocked.com/api/forecast/fr.73440?app_id=34ef604f&app_key=9e15b25a985bb34ddc8db6f973324353");
            snowReport = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/snowreport/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        peakData = new HomeData("upper", generalData.getJSONArray("Days").getJSONObject(0), data.getJSONArray("forecast"));
        baseData = new HomeData("base", generalData.getJSONArray("Days").getJSONObject(0), data.getJSONArray("forecast"));
        weeklyData = new WeeklyData(generalData.getJSONArray("Days"));
        snowData = new SnowData(snowReport);

    }

    @Override
    public void refresh() {
        try {
            data = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/resortforecast/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
            generalData = URLConnectionReader.getJSON("http://api.weatherunlocked.com/api/forecast/fr.73440?app_id=34ef604f&app_key=9e15b25a985bb34ddc8db6f973324353");
            snowReport = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/snowreport/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //refreshes the homeData
        //data for the current timestamp
        JSONObject nonLocationSpecificData = data.getJSONArray("forecast").getJSONObject(0);

        //----refreshes the peakData
        JSONObject upperSpecificData = data.getJSONArray("forecast").getJSONObject(0).getJSONObject("upper");

        peakData.setWeatherIcon(upperSpecificData.getString("wx_icon"));
        peakData.setCurrentTemperature(upperSpecificData.getInt("temp_c"));
        peakData.setWeatherCondition(upperSpecificData.getString("wx_desc"));
        peakData.setWindSpeed(upperSpecificData.getInt("windspd_kmh"));

        peakData.setCloudCoverage(nonLocationSpecificData.getInt("highcloud_pct"));
        peakData.setVisibility(nonLocationSpecificData.getInt("vis_km"));

        peakData.setTimeline(data.getJSONArray("forecast"));

        peakData.setSunrise(generalData.getJSONArray("Days").getJSONObject(0).getString("sunrise_time"));

        //----refreshes the baseData
        JSONObject baseSpecificData = data.getJSONArray("forecast").getJSONObject(0).getJSONObject("base");

        baseData.setWeatherIcon(baseSpecificData.getString("wx_icon"));
        baseData.setCurrentTemperature(baseSpecificData.getInt("temp_c"));
        baseData.setWeatherCondition(baseSpecificData.getString("wx_desc"));
        baseData.setWindSpeed(baseSpecificData.getInt("windspd_kmh"));

        baseData.setCloudCoverage(nonLocationSpecificData.getInt("lowloud_pct"));
        baseData.setVisibility(nonLocationSpecificData.getInt("vis_km"));

        baseData.setTimeline(data.getJSONArray("forecast"));

        baseData.setSunrise(generalData.getJSONArray("Days").getJSONObject(0).getString("sunrise_time"));

        //----delete if not extra widgets
        peakData.setSunset(generalData.getJSONArray("Days").getJSONObject(0).getString("sunset_time"));
        peakData.setHumidity(nonLocationSpecificData.getInt("hum_pct"));
        peakData.setWindDirection(upperSpecificData.getString("winddir_compass"));
        peakData.setFreshSnow(upperSpecificData.getInt("freshsnow_cm"));
        baseData.setSunset(generalData.getJSONArray("Days").getJSONObject(0).getString("sunset_time"));
        baseData.setHumidity(nonLocationSpecificData.getInt("hum_pct"));
        baseData.setWindDirection(baseSpecificData.getString("winddir_compass"));
        baseData.setFreshSnow(baseSpecificData.getInt("freshsnow_cm"));

        //refreshes the weeklyData
        weeklyData.setWeek(generalData.getJSONArray("Days"));

        //refreshes the snowData
        snowData.setSnowConditions(snowReport.getString("conditions"));
        snowData.setLastSnowed(snowReport.getString("lastsnow"));
        snowData.setPercentageOpenRuns(snowReport.getInt("pctopen"));
        snowData.setSnowFallTop(snowReport.getDouble("uppersnow_cm"));
        snowData.setSnowFallBottom(snowReport.getDouble("lowersnow_cm"));
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
}
