package uk.ac.cam.gr3.weather.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import uk.ac.cam.gr3.weather.gui.util.HSwipeContainer;

public class FrameController {

    @FXML
    private AnchorPane swipeAnchor;

    @FXML
    private Button menu;

    @FXML
    private Button snow, home, weekly;

    private HSwipeContainer container;

    public void setSwipeContainer(HSwipeContainer container) {

        this.container = container;
        swipeAnchor.getChildren().setAll(this.container);

        AnchorPane.setTopAnchor(container, 0d);
        AnchorPane.setRightAnchor(container, 0d);
        AnchorPane.setBottomAnchor(container, 0d);
        AnchorPane.setLeftAnchor(container, 0d);
    }

    @FXML
    private void showSnowReport() {

        container.setDisplaying(HSwipeContainer.SNOW_REPORT);
    }

    @FXML
    private void showHomeScreen() {

        container.setDisplaying(HSwipeContainer.HOME_SCREEN);
    }

    @FXML
    private void showWeeklyReport() {

        container.setDisplaying(HSwipeContainer.WEEKLY_REPORT);
    }
}
