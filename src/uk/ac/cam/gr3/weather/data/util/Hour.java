package uk.ac.cam.gr3.weather.data.util;

//Creates a hour for the temperature timeline on the home page.
public class Hour {
    //If you want to access these use the getter methods below.
    private String hour; //24 hour clock in the format HH:MM
    private int temperture;
    private String weatherIcon;

    //constructor
    public Hour(String hour, int temperture, String weatherIcon){
        refresh(hour, temperture, weatherIcon);
    }

    //Refreshes the data corresponding to the hour.
    public void refresh(String hour, int temperture, String weatherIcon) {
        this.hour = hour;
        this.temperture = temperture;
        this.weatherIcon = weatherIcon;
    }

    //getter methods
    public String getHour() {
        return hour;
    }

    public int getTemperture() {
        return temperture;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
