package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class HomeScreenController {

    @FXML
    private ToggleGroup altitudeSelect;

    public void init() {

        altitudeSelect.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                oldValue.setSelected(true);
        });
    }
}
