package striker.crypto_converter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConverterApp extends Application{

    private Stage stage;
    private Scene scene;
    
    private VBox root;
    private TextField currencyField;
    private TextField countryField;
    private Label finalConversionLabel;
    private Button convertButton;

    public ConverterApp() {
        this.root = new VBox(10);
        this.currencyField = new TextField("Enter a crypto currency!");
        this.countryField = new TextField("Enter a country!");
        this.finalConversionLabel = new Label("Your conversion will show up here.");
        this.convertButton = new Button("Convert!");
    }
    @Override
    public void init() {
        this.root.getChildren().addAll(this.currencyField, this.countryField, this.finalConversionLabel, this.convertButton);
        setVGrowMultiple(root, this.currencyField, this.countryField, this.finalConversionLabel, this.convertButton);
    }
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

    private void setVGrowMultiple(VBox vBox, Node... nodes) {
        for (Node node: nodes) {
            vBox.setVgrow(node, Priority.ALWAYS);
        }
    }
    
}
