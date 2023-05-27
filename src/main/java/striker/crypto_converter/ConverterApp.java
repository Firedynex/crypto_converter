package striker.crypto_converter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConverterApp extends Application{

    private Stage stage;
    private Scene scene;
    
    private VBox root;
    private TextInputComponent currencyField;
    private TextInputComponent countryField;
    private Label finalConversionLabel;
    private Button convertButton;

    private Converter converter;

    public ConverterApp() {
        this.root = new VBox(10);
        this.currencyField = new TextInputComponent("Cryptocurrency:", "Enter a cryptocurrency code!");
        this.countryField = new TextInputComponent("Country: ", "Enter a country!");
        this.finalConversionLabel = new Label("Your conversion will show up here.");
        this.convertButton = new Button("Convert!");
        this.converter = new Converter();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        this.root.getChildren().addAll(this.currencyField, this.countryField, this.finalConversionLabel, this.convertButton);
        setVGrowMultiple(root, this.currencyField, this.countryField, this.finalConversionLabel, this.convertButton);
        EventHandler<ActionEvent> convertCurrency = (ActionEvent e) -> {
            Runnable convertRunnable = () -> {
                convert();
            };
            Thread convertThread = new Thread(convertRunnable);
            convertThread.setDaemon(true);
            convertThread.start();
        };
        this.convertButton.setOnAction(convertCurrency);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        scene = new Scene(root);

        stage.setTitle("Crypto Converter App");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
    }

    /**
     * Method that makes all the nodes in a VBox grow to an appropriate size.
     */
    private void setVGrowMultiple(VBox vBox, Node... nodes) {
        for (Node node: nodes) {
            vBox.setVgrow(node, Priority.ALWAYS);
        }
    }

    /**
     * Converts the cryptocurrency into the user's desired currency.
     */
    public void convert() {
        String userCurrency = currencyField.getTextField().getText();
        try {
            if (converter.validCurrency(userCurrency)) {
                double usdPrice = converter.getPrice(userCurrency);
                String country = converter.getCountrySymbol(countryField.getTextField().getText());
                String convertedPrice = String.valueOf(converter.convert(country, usdPrice));
                Platform.runLater(() -> finalConversionLabel.setText(convertedPrice.toString()));
            } else {
                throw new IllegalArgumentException("Desired currency is not valid!");
            }
        } catch (Throwable e) {
            TextArea errorText = new TextArea(e.getMessage());
            errorText.setEditable(false);
            errorText.setWrapText(true);
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.getDialogPane().setContent(errorText);
                alert.setResizable(true);
                alert.showAndWait();
            });
        }
    }
}
