package uk.ac.cam.gr3.weather.gui.util;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import uk.ac.cam.gr3.weather.Util;

public class SwipeContainer extends Region {

    public static final int SNOW_REPORT = 0, HOME_SCREEN = 1, WEEKLY_REPORT = 2;

    private final HBox content;
    private final int screenWidth;

    private final DoubleBinding displayingNormalised;

    public DoubleBinding displayingNormalisedProperty() {
        return displayingNormalised;
    }

    private TranslateTransition transition;

    private double dragStartX;
    private double dragStartY;

    private boolean canScroll;
    private boolean isScrolling;

    public SwipeContainer(Region snowReport, Region homeScreen, Region weeklyReport, int screenWidth) {

        this.screenWidth = screenWidth;

        content = new HBox(snowReport, homeScreen, weeklyReport);

        displayingNormalised = content.translateXProperty().divide(-screenWidth);

        setDisplaying(HOME_SCREEN);

        getChildren().add(content);

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            if (transition != null) {
                transition.stop();
                transition = null;

                canScroll = false;
            } else {
                double containerY = e.getY();
                dragStartY = containerY - getScreen(getDisplayingRounded()).getTranslateY();

                canScroll = true;
            }

            isScrolling = false;

            double containerX = e.getX();
            dragStartX = containerX - content.getTranslateX();
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            double containerX = e.getX();
            double translateX = containerX - dragStartX;

            double containerY = e.getY();
            double translateY = containerY - dragStartY;

            if (canScroll && !isScrolling) {

                double dx = translateX - content.getTranslateX();
                double dy = translateY - getScreen(getDisplayingRounded()).getTranslateY();

                isScrolling = Math.abs(dy / dx) > 1;
                canScroll = false;
            }

            if (isScrolling) {
                Region screen = getScreen(getDisplayingRounded());

                double diff = getHeight() - screen.getHeight();

                double clamped = diff < 0 ? Util.clamp(translateY, diff, 0) : 0;
                screen.setTranslateY(clamped);
            } else {
                content.setTranslateX(Util.clamp(translateX, -2 * screenWidth, 0));
            }
        });

        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {

            setDisplaying(getDisplayingRounded());
        });
    }

    private Region getScreen(int displayingRounded) {
        return (Region) content.getChildren().get(displayingRounded);
    }

    private int getDisplayingRounded() {
        return (int) Math.round(displayingNormalised.get());
    }

    public void setDisplaying(int displaying) {
        double target = -displaying * this.screenWidth;
        double current = content.getTranslateX();

        if (transition != null)
            transition.stop();

        double translateBy = target - current;

        transition = new TranslateTransition(Duration.millis(Math.abs(translateBy) / 2), content);

        transition.setByX(translateBy);
        transition.setOnFinished(event -> {
            transition = null;
            for (int i = 0; i < 3; i++) {
                if (i != displaying)
                    getScreen(i).setTranslateY(0);
            }
        });

        transition.play();
    }
}
