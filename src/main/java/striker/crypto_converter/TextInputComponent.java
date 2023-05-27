package striker.crypto_converter;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TextInputComponent extends HBox{
    private Label label;
    private TextField textField;
    
    /**
     * Constructor that initializes instance variables.
     */
    public TextInputComponent(String labelString, String textInputString) {
        super(10);
        this.label = new Label(labelString);
        this.textField = new TextField(textInputString);
        HBox.setHgrow(label, Priority.ALWAYS);
        HBox.setHgrow(textField, Priority.ALWAYS);
        this.getChildren().addAll(this.label, this.textField);
    }

    /**
     * Accessor method for {@code label}.
     */
    public Label getLabel() {
        return this.label;
    }

    /**
     * Accessor method for {@code textField}.
     */
    public TextField getTextField() {
        return this.textField;
    }
}
