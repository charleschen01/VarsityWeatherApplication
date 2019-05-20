package uk.ac.cam.gr3.weather.gui.util;

import javafx.application.Platform;
import javafx.fxml.FXML;
import uk.ac.cam.gr3.weather.data.WeatherService;

public abstract class FXMLController {

    protected final WeatherService service;

    protected FXMLController(WeatherService service) {

        this.service = service;

        service.addRefreshListener(() -> Platform.runLater(this::update));
    }

    @FXML
    protected abstract void initialize();

    protected abstract void update();
}
