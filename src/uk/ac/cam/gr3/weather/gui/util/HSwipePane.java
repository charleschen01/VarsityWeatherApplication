package uk.ac.cam.gr3.weather.gui.util;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import uk.ac.cam.gr3.weather.Util;

public class HSwipePane extends Region {

    private final Region content;
    private double dragStartX;

    public HSwipePane(Region content) {

        this.content = content;
        getChildren().add(content);

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            double containerX = e.getX();
            dragStartX = containerX - this.content.getTranslateX();

            e.consume();
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            double containerX = e.getX();
            double translateX = containerX - dragStartX;

            double diff = getWidth() - this.content.getWidth();

            this.content.setTranslateX(Util.clamp(translateX, diff, 0));

            e.consume();
        });
    }
}
