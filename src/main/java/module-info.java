module striker.crypto_converter {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive java.logging;
    requires transitive java.net.http;
    requires transitive com.google.gson;

    opens striker.crypto_converter to com.google.gson;
    exports striker.crypto_converter;
}
