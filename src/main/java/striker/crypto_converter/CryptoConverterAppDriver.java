package striker.crypto_converter;

/**
 * Driver class to run the app.
 */
public class CryptoConverterAppDriver {
    public static void main(String[] args) {
        Converter converter = new Converter();
        System.out.println(converter.convert("EUR", 200));
        
    }

}