package uk.ac.cam.gr3.weather.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WeeklyReportController {

    @FXML
    private VBox WeekVBox;

    public void init() {

        HBox Tuesday = new HBox();
        WeekVBox.getChildren().add(Tuesday);
    }
}
