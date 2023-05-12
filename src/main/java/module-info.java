module striker.crypto_converter {
    requires javafx.controls;
    requires javafx.fxml;

    opens striker.crypto_converter to javafx.fxml;
    exports striker.crypto_converter;
}
