package uk.ac.cam.gr3.weather.gui.util;

import uk.ac.cam.gr3.weather.data.WeatherService;

public abstract class FXMLController {

    protected final WeatherService service;

    protected FXMLController(WeatherService service) {

        this.service = service;
    }

    protected abstract void initialize();
}
