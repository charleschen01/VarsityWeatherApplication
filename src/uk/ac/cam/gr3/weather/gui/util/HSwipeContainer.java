package uk.ac.cam.gr3.weather.gui.util;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import uk.ac.cam.gr3.weather.Util;

public class HSwipeContainer extends Region {

    public static final int LEFT = 0, CENTRE = 1, RIGHT = 2;

    private final HBox content;
    private final int screenWidth;
    private TranslateTransition transition;

    private double dragStartX;

    public HSwipeContainer(Node left, Node centre, Node right, int screenWidth) {

        this.screenWidth = screenWidth;

        content = new HBox(left, centre, right);

        setDisplaying(CENTRE);

        getChildren().add(content);

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            if (transition != null) {
                transition.stop();
                transition = null;
            }

            double containerX = e.getX();
            dragStartX = containerX - content.getTranslateX();
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            double containerX = e.getX();
            double translateX = containerX - dragStartX;

            content.setTranslateX(Util.clamp(translateX, -2 * screenWidth, 0));
        });

        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {

            setDisplaying((int) Math.round(-content.getTranslateX() / screenWidth));
        });
    }

    public void setDisplaying(int displaying) {
        double target = -displaying * this.screenWidth;
        double current = content.getTranslateX();

        if (transition != null)
            transition.stop();

        double translateBy = target - current;

        transition = new TranslateTransition(Duration.millis(Math.abs(translateBy) / 2), content);

        transition.setByX(translateBy);
        transition.play();

        transition.setOnFinished(event -> transition = null);
    }

    @Override
    protected double computePrefWidth(double height) {
        return screenWidth;
    }
}
