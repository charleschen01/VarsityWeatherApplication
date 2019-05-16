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
    Day(String date, int lowestTempersture, int highestTempersture, String weatherIcon) {
        //Converts the date into the day of the week
        Date now = null;
        try {
            now = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        this.nameOfDay = simpleDateformat.format(now);

        this.lowestTemperature = lowestTempersture;
        this.highestTemperature = highestTempersture;
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

    void setLowestTemperature(int lowestTempersture) {
        this.lowestTemperature = lowestTempersture;
    }

    void setHighestTemperature(int highestTempersture) {
        this.highestTemperature = highestTempersture;
    }

    void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}

