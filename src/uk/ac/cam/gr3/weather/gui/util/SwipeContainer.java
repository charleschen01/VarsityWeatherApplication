package uk.ac.cam.gr3.weather.gui.util;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import uk.ac.cam.gr3.weather.Util;

// A visual component that has the three screens, allows horizontal swiping to change screens and also
// vertical scrolling where applicable.
public class SwipeContainer extends Region {

    public static final int SNOW_REPORT = 0, HOME_SCREEN = 1, WEEKLY_REPORT = 2;

    private final HBox content;
    private final int screenWidth;

    // The currently displayed screen, normalised to the range 0 -- 2 with fractional part when between two screens.
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
        content.setStyle("-fx-background-image: url('/background_new.png');");

        displayingNormalised = content.translateXProperty().divide(-screenWidth);

        setDisplaying(HOME_SCREEN);

        getChildren().add(content);

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            if (transition != null) {
                // We were in the middle of an automatic transition towards a screen; interrupt it.
                transition.stop();
                transition = null;

                // In this case, we can't scroll as we're in the middle of two screens.
                canScroll = false;
            } else {
                // We could scroll. Record the vertical start position.
                double containerY = e.getY();
                dragStartY = containerY - getScreen(getDisplayingRounded()).getTranslateY();

                canScroll = true;
            }

            // We haven't started scrolling; we'll decide if we actually scroll when the mouse is dragged.
            isScrolling = false;

            // Record the horizontal start position.
            double containerX = e.getX();
            dragStartX = containerX - content.getTranslateX();
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            // Compute horizontal and vertical differences.
            double containerX = e.getX();
            double translateX = containerX - dragStartX;

            double containerY = e.getY();
            double translateY = containerY - dragStartY;

            if (canScroll && !isScrolling) {
                // This happens on the first drag event when we could scroll.

                double dx = translateX - content.getTranslateX();
                double dy = translateY - getScreen(getDisplayingRounded()).getTranslateY();

                // Scroll only if we're moving the mouse more vertically than horizontally.
                isScrolling = Math.abs(dy / dx) > 1;
                canScroll = false;
            }

            if (isScrolling) {
                // Get the currently displayed screen (guaranteed to be exactly on one screen)
                Region screen = getScreen(getDisplayingRounded());

                double diff = getHeight() - screen.getHeight();

                // make sure we don't scroll outside the bounds, and watch out for smaller content than the screen allows.
                double clamped = diff < 0 ? Util.clamp(translateY, diff, 0) : 0;
                screen.setTranslateY(clamped);
            } else {
                content.setTranslateX(Util.clamp(translateX, -2 * screenWidth, 0));
            }
        });

        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            // Force the display back to the nearest screen.
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

        // Compute the target and current horizontal translation
        double target = -displaying * this.screenWidth;
        double current = content.getTranslateX();

        // Stop any ongoing transition
        if (transition != null)
            transition.stop();

        double translateBy = target - current;

        // Create a translation transition with duration proportional to the amount of translation to be applied
        transition = new TranslateTransition(Duration.millis(Math.abs(translateBy) / 2), content);

        transition.setByX(translateBy);
        transition.setOnFinished(event -> {
            // When done, set the transition to null and reset vertical scrolling on all other screens.
            transition = null;
            for (int i = 0; i < 3; i++) {
                if (i != displaying)
                    getScreen(i).setTranslateY(0);
            }
        });

        transition.play();
    }
}
