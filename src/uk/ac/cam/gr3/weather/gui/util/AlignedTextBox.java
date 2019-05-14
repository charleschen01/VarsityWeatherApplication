package uk.ac.cam.gr3.weather.gui.util;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AlignedTextBox extends VBox {
    private Text text;

    public AlignedTextBox(String textInput, int fontSize, Pos alignment) {
        text = new Text(textInput);
        text.setFont(Font.font(fontSize));
        this.getChildren().addAll(text);
        this.setAlignment(alignment);
    }

    public AlignedTextBox(String textInput, int fontSize) {
        this(textInput, fontSize, Pos.BOTTOM_LEFT);
    }

    public void setText(String textInput) {
        text.setText(textInput);
    }
}
