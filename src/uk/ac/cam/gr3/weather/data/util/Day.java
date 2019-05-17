package uk.ac.cam.gr3.weather.data.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Creates a day for the weekly report.
public class Day {
    //If you want to access these use the getter methods below.
    private String nameOfDay;
    private int lowestTemperature;
    private int highestTemperature;
    private String weatherIcon;

    //constructor
    Day(String date, int lowestTemperature, int highestTemperature, String weatherIcon) {
        //Converts the date into the day of the week
        Date now = null;
        try {
            now = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        this.nameOfDay = simpleDateformat.format(now);

        this.lowestTemperature = lowestTemperature;
        this.highestTemperature = highestTemperature;
        this.weatherIcon = weatherIcon;
    }

    //getter methods
    public String getNameOfDay() {
        return nameOfDay;
    }

    public int getLowestTemperature() {
        return lowestTemperature;
    }

    public int getHighestTemperature() {
        return highestTemperature;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }


    //setters
    void setNameOfDay(String date) {
        Date now = null;
        try {
            now = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        this.nameOfDay = simpleDateformat.format(now);
    }

    void setLowestTemperature(int lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    void setHighestTemperature(int highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}

