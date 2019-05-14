package uk.ac.cam.gr3.weather.gui;

import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import uk.ac.cam.gr3.weather.gui.util.HSwipeContainer;

public class FrameController {

    @FXML
    private AnchorPane swipeAnchor;

    @FXML
    private Button menu;

    @FXML
    private Pane menuBar1, menuBar2, menuBar3;

    @FXML
    private Button snow, home, weekly;
    // TODO bottom navigation bar

    private HSwipeContainer container;

    private DoubleProperty menuBarTranslateY = new SimpleDoubleProperty(0);
    private DoubleProperty menuBarRotate = new SimpleDoubleProperty(0);

    private BooleanProperty isShowingMenu = new SimpleBooleanProperty(false);

    private Transition menuToggleTransition;

    // TODO clean up initialisation

    public void setSwipeContainer(HSwipeContainer container) {

        this.container = container;
        swipeAnchor.getChildren().setAll(this.container);

        AnchorPane.setTopAnchor(container, 0d);
        AnchorPane.setRightAnchor(container, 0d);
        AnchorPane.setBottomAnchor(container, 0d);
        AnchorPane.setLeftAnchor(container, 0d);
    }

    public void init() {

        menuBar1.translateYProperty().bind(menuBarTranslateY);
        menuBar3.translateYProperty().bind(menuBarTranslateY.negate());

        menuBar1.rotateProperty().bind(menuBarRotate);
        menuBar3.rotateProperty().bind(menuBarRotate.negate());

        menuBar2.visibleProperty().bind(menuBarRotate.isEqualTo(0));

        Transition translate = new Transition() {

            {
                setCycleDuration(Duration.millis(100));
            }

            @Override
            protected void interpolate(double frac) {
                menuBarTranslateY.set(frac * 6);
            }
        };

        Transition rotate = new Transition() {

            {
                setCycleDuration(Duration.millis(100));
            }

            @Override
            protected void interpolate(double frac) {
                menuBarRotate.set(frac * 45);
            }
        };

        // TODO wrap with a parallel transition to pull the menu in / out of the screen
        menuToggleTransition = new SequentialTransition(translate, rotate);
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

    @FXML
    private void toggleMenu() {

        boolean isShowing = isShowingMenu.get();

        int rate = isShowing ? -1 : 1;

        menuToggleTransition.setRate(rate);
        menuToggleTransition.play();

        isShowingMenu.set(!isShowing);
    }
}
