package uk.ac.cam.gr3.weather.gui.util;

import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class HSwipeContainer extends Region {

    public static final int LEFT = 0, CENTRE = 1, RIGHT = 2;

    private final HBox content;
    private final int screenWidth;
    private TranslateTransition transition;

    public HSwipeContainer(Node left, Node centre, Node right, int screenWidth) {

        this.screenWidth = screenWidth;

        content = new HBox(left, centre, right);

        displaying.addListener((observable, oldValue, newValue) -> {

            double target = -newValue.intValue() * this.screenWidth;
            double current = content.getTranslateX();

            if (transition != null)
                transition.stop();

            double translateBy = target - current;

            transition = new TranslateTransition(Duration.millis(Math.abs(translateBy) / 2), content);

            transition.setByX(translateBy);
            transition.play();

            transition.setOnFinished(event -> transition = null);
        });

        setDisplaying(CENTRE);

        getChildren().add(content);

        addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MouseButton button = e.getButton();

            EventType<SwipeEvent> type;

            if (button == MouseButton.PRIMARY) {
                type = SwipeEvent.SWIPE_LEFT;
            } else if (button == MouseButton.SECONDARY) {
                type = SwipeEvent.SWIPE_RIGHT;
            } else {
                return;
            }

            SwipeEvent event = new SwipeEvent(type, 0, 0, 0, 0, false, false, false, false, false, 1, null);

            e.consume();

            fireEvent(event);
        });

        addEventHandler(SwipeEvent.SWIPE_LEFT, e -> {

            if (getDisplaying() != RIGHT)
                setDisplaying(getDisplaying() + 1);
        });

        addEventHandler(SwipeEvent.SWIPE_RIGHT, e -> {

            if (getDisplaying() != LEFT)
                setDisplaying(getDisplaying() - 1);
        });
    }

    private IntegerProperty displaying = new SimpleIntegerProperty();

    public IntegerProperty displayingProperty() {
        return displaying;
    }

    public int getDisplaying() {
        return displaying.get();
    }

    public void setDisplaying(int displaying) {
        this.displaying.set(displaying);
    }

    @Override
    protected double computePrefWidth(double height) {
        return screenWidth;
    }
}
