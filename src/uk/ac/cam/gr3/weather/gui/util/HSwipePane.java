package uk.ac.cam.gr3.weather.gui.util;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import uk.ac.cam.gr3.weather.Util;

// A visual components that allows horizontal scrolling on one child component.
public class HSwipePane extends Region {

    private final Region content;
    private double dragStartX;

    public HSwipePane(Region content) {

        this.content = content;
        getChildren().add(content);

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            // Record the start position for dragging horizontally
            double containerX = e.getX();
            dragStartX = containerX - this.content.getTranslateX();

            e.consume();
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            // Compute the difference from the start position
            double containerX = e.getX();
            double translateX = containerX - dragStartX;

            double diff = getWidth() - this.content.getWidth();

            // Set horizontal translation, making sure the result doesn't go off bounds
            this.content.setTranslateX(Util.clamp(translateX, diff, 0));

            e.consume();
        });
    }
}
