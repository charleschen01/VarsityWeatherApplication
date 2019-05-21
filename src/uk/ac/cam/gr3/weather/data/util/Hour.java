package uk.ac.cam.gr3.weather.data.util;

//Creates a hour for the temperature timeline on the home page.
public class Hour {
    //If you want to access these use the getter methods below.
    private final String hour; //24 hour clock in the format HH:MM
    private final int temperature;
    private final String weatherIcon;

    //constructor
    Hour(String hour, int temperature, String weatherIcon){
        this.hour = hour;
        this.temperature = temperature;
        this.weatherIcon = weatherIcon;
    }

    //getters
    public String getHour() {
        return hour;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
