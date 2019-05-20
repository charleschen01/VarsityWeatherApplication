package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import uk.ac.cam.gr3.weather.Util;
import uk.ac.cam.gr3.weather.data.WeatherService;
import uk.ac.cam.gr3.weather.gui.util.FXMLController;
import uk.ac.cam.gr3.weather.gui.util.SwipeContainer;

public class FrameController extends FXMLController {

    private SwipeContainer container;

    private DoubleProperty menuBarTranslateY = new SimpleDoubleProperty(0);
    private DoubleProperty menuBarRotate = new SimpleDoubleProperty(0);

    private BooleanProperty isShowingMenu = new SimpleBooleanProperty(false);

    private Transition menuToggleTransition;

    private RotateTransition refreshSpinAnimation;

    public FrameController(WeatherService service) {
        super(service);
    }

    @FXML
    private AnchorPane swipeAnchor;

    @FXML
    private Button menu;

    @FXML
    private Pane menuBar1, menuBar2, menuBar3;

    @FXML
    private SVGPath refreshSpinner;

    @FXML
    private Pane bottomNavigationSelection;

    @Override
    protected void initialize() {

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

        refreshSpinAnimation = new RotateTransition(Duration.millis(500), refreshSpinner);
        refreshSpinAnimation.setByAngle(360);

        refreshSpinAnimation.setAutoReverse(true);
        refreshSpinAnimation.setInterpolator(Interpolator.LINEAR);
    }

    public void setSwipeContainer(SwipeContainer container) {

        this.container = container;
        swipeAnchor.getChildren().setAll(this.container);

        Util.fitToAnchorPane(container);

        bottomNavigationSelection.translateXProperty().bind(container.displayingNormalisedProperty().subtract(1).multiply(70));
    }

    @FXML
    private void showSnowReport() {

        container.setDisplaying(SwipeContainer.SNOW_REPORT);
    }

    @FXML
    private void showHomeScreen() {

        container.setDisplaying(SwipeContainer.HOME_SCREEN);
    }

    @FXML
    private void showWeeklyReport() {

        container.setDisplaying(SwipeContainer.WEEKLY_REPORT);
    }

    @FXML
    private void toggleMenu() {

        boolean isShowing = isShowingMenu.get();

        int rate = isShowing ? -1 : 1;

        menuToggleTransition.setRate(rate);
        menuToggleTransition.play();

        isShowingMenu.set(!isShowing);
    }

    @FXML
    private void refresh() {

        refreshSpinAnimation.setOnFinished(event -> refreshSpinAnimation.play());
        refreshSpinAnimation.playFromStart();

        new Thread(() -> {
            service.refresh();
            Platform.runLater(this::update);

            refreshSpinAnimation.setOnFinished(null);
        }).start();
    }

    @Override
    public void update() {
        container.updateDisplay();
    }
}
