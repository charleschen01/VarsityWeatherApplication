package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import uk.ac.cam.gr3.weather.Util;
import uk.ac.cam.gr3.weather.gui.util.HSwipePane;

public class HomeScreenController {

    @FXML
    private ToggleGroup altitudeSelect;

    @FXML
    private ImageView weatherMood;

    @FXML
    private Label currentTemperature;

    @FXML
    private AnchorPane graphSwipeAnchor;

    public void setHSwipePane(HSwipePane pane) {

        graphSwipeAnchor.getChildren().setAll(pane);

        Util.fitToAnchorPane(pane);
    }

    public void init() {

        altitudeSelect.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                oldValue.setSelected(true);
        });

        Platform.runLater(() -> {
            ObjectBinding<Rectangle> clipBinding = Bindings.createObjectBinding(() -> new Rectangle(0, 0, graphSwipeAnchor.getWidth(), graphSwipeAnchor.getHeight()), graphSwipeAnchor.layoutBoundsProperty());
            graphSwipeAnchor.clipProperty().bind(clipBinding);
        });
    }
}
