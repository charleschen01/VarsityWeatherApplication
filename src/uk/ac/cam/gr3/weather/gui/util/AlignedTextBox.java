package uk.ac.cam.gr3.weather.gui.util;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AlignedTextBox extends VBox {
    private Text text;

    public AlignedTextBox(String textInput, int fontSize) {
        text = new Text(textInput);
        text.setFont(Font.font(fontSize));
        this.getChildren().addAll(text);
    }

    public AlignedTextBox(String textInput, int fontSize, Pos alignment) {
        this(textInput, fontSize);
        this.setAlignment(alignment);
    }

    public void setText(String textInput) {
        text.setText(textInput);
    }
}
