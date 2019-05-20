package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.ac.cam.gr3.weather.data.WeatherService;
import uk.ac.cam.gr3.weather.data.util.SnowData;
import uk.ac.cam.gr3.weather.gui.util.FXMLController;

public class SnowReportController extends FXMLController {

    public SnowReportController(WeatherService service) {
        super(service);
    }

    @FXML
    private Label snowConditions;

    @FXML
    private Label lastSnowed;

    @FXML
    private Label percentageOpenRuns;

    @FXML
    private Label topSnow, bottomSnow;

    @Override
    protected void initialize() {

        update();
    }

    public void update() {

        SnowData snowData = service.getSnowData();

        snowConditions.setText(snowData.getSnowConditions());
        lastSnowed.setText(snowData.getLastSnowed());
        percentageOpenRuns.setText(Integer.toString(snowData.getPercentageOpenRuns()));
        topSnow.setText(String.format("%.1f", snowData.getSnowFallTop()));
        bottomSnow.setText(String.format("%.1f", snowData.getSnowFallBottom()));
    }
}
