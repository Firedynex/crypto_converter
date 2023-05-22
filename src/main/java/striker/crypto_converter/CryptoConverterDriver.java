package striker.crypto_converter;

import javafx.application.Application;

public class CryptoConverterDriver {
    public static void main(String[] args) {
        try {
            Application.launch(ConverterApp.class, args);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }
}
